package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import com.tp_anual_dds.broker.MensajeIncidente;
import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.incidente.Alerta;
import com.tp_anual_dds.domain.incidente.Alerta.TipoAlerta;
import com.tp_anual_dds.domain.incidente.FallaTecnica;
import com.tp_anual_dds.domain.incidente.Incidente;
import com.tp_anual_dds.domain.suscripcion.GestorSuscripciones;
import com.tp_anual_dds.domain.suscripcion.Suscripcion;
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
    public void notificarColaborador(Suscripcion suscripcion, String asunto, String cuerpo) {
        MedioDeContacto medioDeContacto = suscripcion.getMedioDeContactoElegido();  // Usa el Medio de Contacto previamente elegido por el colaborador
        medioDeContacto.contactar(asunto, cuerpo);
    }

    @Override
    public void verificarCondiciones() {
        GestorSuscripciones gestorSuscripciones = Sistema.getGestorSuscripciones();
        ArrayList<Suscripcion> suscripciones = gestorSuscripciones.suscripcionesPorHeladera(this);
        
        for (Suscripcion suscripcion : suscripciones) {
            // Verifica si se está vaciando
            if(viandasActuales() <= suscripcion.getViandasDisponiblesMin()) {
                notificarColaborador(
                    suscripcion,
                    "La heladera " + nombre + " se está vaciando.",
                    "La heladera en cuestión tiene " + viandasActuales() + " viandas disponibles. " +
                    "Sería conveniente traer viandas de la heldera %o");
                    // TODO Completar %o con la Heladera más llena de las cercanas
            }
            
            // Verifica si se está llenando
            if((capacidad - viandas.size()) >= suscripcion.getViandasParaLlenarMax()) {
                notificarColaborador(
                    suscripcion, "La heladera " + nombre + " está casi llena.",
                    "Faltan " + (capacidad - viandasActuales()) + " viandas para llenar la heladera en cuestión. " +
                    "Sería conveniente llevar viandas a la heladera %o");
                    // TODO Completar %o con la Heladera menos llena de las cercanas
            }

            // Verifica si hay un desperfecto
            if(suscripcion.getNotificarDesperfecto() && !estado) {
                notificarColaborador(
                    suscripcion, "La heladera "+ nombre + " ha sufrido un desperfecto.",
                    "Las viandas deben ser trasladadas a %s.");
                    // TODO Completar %s con la Heladera/s más cercanas
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
    public void producirAlerta(TipoAlerta tipo) {
        marcarComoInactiva();   // Si una Alerta debe ser reportada, previamente, se marca la Heladera como inactiva

        Alerta alerta = new Alerta(LocalDateTime.now(), this, tipo);
        alerta.darDeAlta();

        reportarIncidente(alerta);
    }

    @Override
    public void producirFallaTecnica(Colaborador colaborador, String descripcion, String foto) {
        marcarComoInactiva();

        FallaTecnica fallaTecnica = new FallaTecnica(LocalDateTime.now(), this, colaborador, descripcion, foto);
        fallaTecnica.darDeAlta();

        reportarIncidente(fallaTecnica);
    }

    @Override
    public void reportarIncidente(Incidente incidente) {
        MensajeIncidente mensajeIncidente = new MensajeIncidente(this, incidente);
        
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
