package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import com.tp_anual_dds.broker.MensajeEstado;
import com.tp_anual_dds.broker.MensajeIncidente;
import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.incidente.Alerta;
import com.tp_anual_dds.domain.incidente.Alerta.TipoAlerta;
import com.tp_anual_dds.domain.incidente.FallaTecnica;
import com.tp_anual_dds.domain.incidente.Incidente;
import com.tp_anual_dds.domain.suscripcion.GestorSuscripciones;
import com.tp_anual_dds.domain.suscripcion.Suscripcion;
import com.tp_anual_dds.domain.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class HeladeraActiva extends Heladera {
    public HeladeraActiva(String vNombre, Ubicacion vUbicacion, ArrayList<Vianda> vViandas, Integer vCapacidad, LocalDateTime vFechaApertura, Float vTempMin, Float vTempMax) {
        nombre = vNombre;
        ubicacion = vUbicacion;
        viandas = vViandas;
        capacidad = vCapacidad;
        fechaApertura = vFechaApertura;
        tempMin = vTempMin;
        tempMax = vTempMax;
        tempActual = 0f;
        estado = true;
        gestorDeAperturas = new GestorDeAperturas();
    }
    
    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    @Override
    public ArrayList<Vianda> getViandas() {
        return viandas;
    }

    @Override
    public Integer getCapacidad() {
        return capacidad;
    }

    @Override
    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    @Override
    public Float getTempMin() {
        return tempMin;
    }

    @Override public Float getTempMax() {
        return tempMax;
    }

    @Override
    public Float getTempActual() {
        return tempActual;
    }

    @Override
    public Boolean getEstado() {
        return estado;
    }

    @Override
    public GestorDeAperturas getGestorDeAperturas() {
        return gestorDeAperturas;
    }

    @Override
    public void setEstado(Boolean nuevoEstado) {
        estado = nuevoEstado;
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
    public Boolean verificarCapacidad() {
        return viandasActuales() < capacidad;
    }

    @Override
    public void verificarCondiciones() {
        GestorSuscripciones gestorSuscripciones = Sistema.getGestorSuscripciones();
        ArrayList<Suscripcion> suscripciones = gestorSuscripciones.suscripcionesPorHeladera(this);
        
        for (Suscripcion suscripcion : suscripciones) {
            // Verifico si se está vaciando
            if(viandasActuales() <= suscripcion.getViandasDisponiblesMin()) {
                reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion.VIANDAS_MIN, suscripcion.getMedioDeContactoElegido());
            }
            
            // Verifico si se está llenando
            if((capacidad - viandas.size()) >= suscripcion.getViandasParaLlenarMax()) {
                reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion.VIANDAS_MAX, suscripcion.getMedioDeContactoElegido());
                
            }

            // Verifico si hay un desperfecto
            if(suscripcion.getNotificarDesperfecto() && !estado) {
                reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion.DESPERFECTO, suscripcion.getMedioDeContactoElegido());
            }
        }
    }

    @Override
    public void agregarVianda(Vianda vianda) {
        if (!verificarCapacidad()) {
            throw new IllegalStateException("No se puede agregar la vianda. Se superaría la capacidad de la heladera " + nombre);
        }
        viandas.add(vianda);
        verificarCondiciones(); // Verifica condiciones cuando agregamos una Vianda (una de las dos únicas formas en que la cantidad de Viandas en la Heladera puede cambiar)
    }

    @Override
    public Vianda retirarVianda() {
        if(estaVacia()) {
            throw new IllegalStateException("La heladera " + nombre + " no tiene más viandas para retirar");
        }
        
        Vianda viandaRetirada = viandas.removeFirst();
        verificarCondiciones(); // Verifica condiciones cuando retiramos una Vianda (una de las dos únicas formas en que la cantidad de Viandas en la Heladera puede cambiar)
        return viandaRetirada;
    }

    @Override
    public void verificarTempActual() {
        if(tempActual < tempMin || tempActual > tempMax) {
            producirAlerta(TipoAlerta.TEMPERATURA);
        }
    }

    @Override
    public void setTempActual(Float temperatura) {
        tempActual = temperatura;
        verificarTempActual();  // Siempre que setea / actualiza su temperatura, debe chequearla posteriormente
    }

    @Override
    public void marcarComoInactiva() {
        setEstado(false);
    }

    @Override
    public void reaccionarAnteIncidente() {
        marcarComoInactiva();
        verificarCondiciones();
    }

    @Override
    public void producirAlerta(TipoAlerta tipo) {
        reaccionarAnteIncidente();  // Si una Alerta debe ser reportada, previamente, se marca la Heladera como inactiva y se avisa a los Colaboradores suscriptos

        Alerta alerta = new Alerta(LocalDateTime.now(), this, tipo);
        alerta.darDeAlta();

        reportarIncidente(alerta);
    }

    @Override
    public void producirFallaTecnica(Colaborador colaborador, String descripcion, String foto) {
        reaccionarAnteIncidente();  // Si una Falla Técnica debe ser reportada, previamente, se marca la Heladera como inactiva y se avisa a los Colaboradores suscriptos

        FallaTecnica fallaTecnica = new FallaTecnica(LocalDateTime.now(), this, colaborador, descripcion, foto);
        fallaTecnica.darDeAlta();

        reportarIncidente(fallaTecnica);
    }

    @Override
    public void reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion condicion, MedioDeContacto medioDeContactoElegido) {   // Usa el Medio de Contacto previamente elegido por el colaborador
        MensajeEstado mensajeEstado = new MensajeEstado(this, condicion, medioDeContactoElegido);

        // Envío al Broker el Mensaje de Estado
        new Thread( () -> {
            try {
                Sistema.getBroker().agregarMensaje(mensajeEstado);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("El hilo fue interrumpido: " + e.getMessage()); // TODO Chequear si está bien que lo tire en System.err
            }
        }).start();
    }

    @Override
    public void reportarIncidente(Incidente incidente) {
        MensajeIncidente mensajeIncidente = new MensajeIncidente(this, incidente);
        
        // Envío al Broker el Mensaje de Incidente
        new Thread( () -> {
            try {
                Sistema.getBroker().agregarMensaje(mensajeIncidente);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("El hilo fue interrumpido: " + e.getMessage()); // TODO Chequear si está bien que lo tire en System.err
            }
        }).start();
    }
}
