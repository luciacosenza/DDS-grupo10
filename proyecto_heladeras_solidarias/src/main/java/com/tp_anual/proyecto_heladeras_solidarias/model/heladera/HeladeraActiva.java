package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta.TipoAlerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.FallaTecnica;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.GestorDeSuscripciones;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import jakarta.persistence.*;
import lombok.extern.java.Log;

@Entity
@Log
public class HeladeraActiva extends Heladera {
    
    public HeladeraActiva(String vNombre, Ubicacion vUbicacion, ArrayList<Vianda> vViandas, Integer vCapacidad, LocalDateTime vFechaApertura, Float vTempMin, Float vTempMax) {
        super(vNombre, vUbicacion, vCapacidad, vTempMin, vTempMax, vViandas, 0f, vFechaApertura, true);
        gestorDeAperturas = new GestorDeAperturas(this);
    }

    @Override
    public void darDeAlta() {
        Sistema.agregarHeladera(this);
    }

    @Override
    public void darDeBaja() {
        Sistema.eliminarHeladera(this);
    }

    @Override
    public Boolean estaVacia() {
        return viandas.isEmpty();
    }

    @Override
    public Boolean estaLlena() {
        return Objects.equals(viandasActuales(), capacidad);
    }

    @Override
    public Integer viandasActuales() {
        return viandas.size();
    }

    @Override
    protected Boolean verificarCapacidad() {
        return viandasActuales() < capacidad;
    }

    @Override
    protected void verificarCondiciones() {
        GestorDeSuscripciones gestorDeSuscripciones = Sistema.getGestorDeSuscripciones();
        ArrayList<Suscripcion> suscripciones = gestorDeSuscripciones.suscripcionesPorHeladera(this);
        
        for (Suscripcion suscripcion : suscripciones) {
            // Verifico si se está vaciando
            if (viandasActuales() <= suscripcion.getViandasDisponiblesMin())
                reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion.VIANDAS_MIN, suscripcion.getMedioDeContactoElegido());
            
            // Verifico si se está llenando
            if ((capacidad - viandasActuales()) <= suscripcion.getViandasParaLlenarMax())
                reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion.VIANDAS_MAX, suscripcion.getMedioDeContactoElegido());

            // Verifico si hay un desperfecto
            if (!estado && suscripcion.getNotificarDesperfecto())
                reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion.DESPERFECTO, suscripcion.getMedioDeContactoElegido());
        }
    }

    @Override
    public void agregarVianda(Vianda vianda) {
        if (!verificarCapacidad()) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.HeladeraActiva.agregarVianda_err", nombre));
            throw new IllegalStateException(I18n.getMessage("heladera.HeladeraActiva.agregarVianda_exception"));
        }

        viandas.add(vianda);
        verificarCondiciones(); // Verifica condiciones cuando agregamos una Vianda (una de las dos únicas formas en que la cantidad de Viandas en la Heladera puede cambiar)
        log.log(Level.INFO, I18n.getMessage("heladera.HeladeraActiva.agregarVianda_info", vianda.getComida(), nombre));
    }

    @Override
    public Vianda retirarVianda() {
        if (estaVacia()) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.HeladeraActiva.retirarVianda_err", nombre));
            throw new IllegalStateException(I18n.getMessage("heladera.HeladeraActiva.retirarVianda_exception"));
        }
        
        Vianda viandaRetirada = viandas.removeFirst();
        verificarCondiciones(); // Verifica condiciones cuando retiramos una Vianda (una de las dos únicas formas en que la cantidad de Viandas en la Heladera puede cambiar)
        log.log(Level.INFO, I18n.getMessage("heladera.HeladeraActiva.retirarVianda_info", viandaRetirada.getComida(), nombre));

        return viandaRetirada;
    }

    @Override
    protected void verificarTempActual() {
        if (tempActual < tempMin || tempActual > tempMax)
            producirAlerta(TipoAlerta.TEMPERATURA);
    }

    @Override
    public void setTempActual(Float temperatura) {
        tempActual = temperatura;
        verificarTempActual();  // Siempre que setea / actualiza su temperatura, debe chequearla posteriormente
    }

    @Override
    public void marcarComoInactiva() {
        estado = false;
    }

    @Override
    public void reaccionarAnteIncidente() {
        marcarComoInactiva();
        verificarCondiciones();
    }

    @Override
    public void reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion condicion, MedioDeContacto medioDeContactoElegido) {   // Usa el Medio de Contacto previamente elegido por el colaborador
        Sistema.getNotificadorDeEstado().notificarEstado(this, condicion, medioDeContactoElegido);
    }

    @Override
    public void reportarIncidente(Incidente incidente) {    
        Sistema.getNotificadorDeIncidentes().notificarIncidente(incidente);
    }

    @Override
    public void producirAlerta(TipoAlerta tipo) {
        reaccionarAnteIncidente();  // Si una Alerta debe ser reportada, previamente, se marca la Heladera como inactiva y se avisa a los Colaboradores suscriptos

        Alerta alerta = new Alerta(LocalDateTime.now(), this, tipo);
        alerta.darDeAlta();

        reportarIncidente(alerta);
        log.log(Level.INFO, I18n.getMessage("heladera.HeladeraActiva.producirAlerta_info", alerta.getTipo(), nombre));
    }

    @Override
    public void producirFallaTecnica(Colaborador colaborador, String descripcion, String foto) {
        reaccionarAnteIncidente();  // Si una Falla Técnica debe ser reportada, previamente, se marca la Heladera como inactiva y se avisa a los Colaboradores suscriptos

        FallaTecnica fallaTecnica = new FallaTecnica(LocalDateTime.now(), this, colaborador, descripcion, foto);
        fallaTecnica.darDeAlta();

        reportarIncidente(fallaTecnica);
        log.log(Level.INFO, I18n.getMessage("heladera.HeladeraActiva.producirFallaTecnica_info", nombre));
    }
}