package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.incidente.Alerta;
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
        MedioDeContacto medioDeContacto = suscripcion.getMedioDeContactoElegido();
        medioDeContacto.contactar(asunto, cuerpo);
    }

    @Override
    public void verificarCondiciones() {
        GestorSuscripciones gestorSuscripciones = Sistema.getGestorSuscripciones();
        ArrayList<Suscripcion> suscripciones = gestorSuscripciones.suscripcionesPorHeladera(this);
        
        for (Suscripcion suscripcion : suscripciones) {
            if(viandasActuales() <= suscripcion.getViandasDisponiblesMin()) {
                notificarColaborador(
                    suscripcion,
                    "La Heladera " + this.getNombre() + " se está vaciando.",
                    "La Heladera tiene " + this.viandasActuales() + " viandas disponibles. " +
                    "Sería conveniente traer viandas de la Heladera %o");
                    // Completar %o con la Heladera mas llena de las cercanas
            }
            
            if((capacidad - viandas.size()) <= suscripcion.getViandasParaLlenarMax()) {
                notificarColaborador(
                    suscripcion, "La heladera " + this.getNombre() + " está casi llena.",
                    "Faltan " + (this.getCapacidad() - this.viandasActuales()) + " viandas para llenarla. " +
                    "Sería conveniente llevar viandas a la Heladera %o");
                    // Completar %o con la Heladera menos llena de las cercanas
            }

            if(suscripcion.getNotificarDesperfecto() && !estado) {
                notificarColaborador(
                    suscripcion, "La heladera "+ this.getNombre() + " ha sufrido un desperfecto.",
                    "Las viandas deben ser trasladadas a %s.");
                    // Completar %s con la Heladera/s mas cercanas
            }
        }
    }

    @Override
    public void agregarVianda(Vianda vianda) {
        if (!verificarCapacidad()) {
            throw new IllegalStateException("No se puede agregar la vianda. Se superaría la capacidad de la Heladera");
        }
        viandas.add(vianda);
        verificarCondiciones();
    }

    @Override
    public Vianda retirarVianda() {
        if(estaVacia()) {
            throw new IllegalStateException("La Heladera no tiene más viandas para retirar");
        }
        
        Vianda viandaRetirada = viandas.removeFirst();
        verificarCondiciones();
        return viandaRetirada;
    }

    @Override
    public void verificarTempActual() {
        if(tempActual < tempMin || tempActual > tempMax) {
            reportarTemperatura();
        }
    }

    @Override
    public void setTempActual(Float temperatura) {
        tempActual = temperatura;
        verificarTempActual();
    }

    @Override
    public void desactivar() {
        setEstado(false);
    }

    @Override
    public void reportarAlerta(Alerta.TipoAlerta tipo) {
        desactivar();

        Alerta alerta = new Alerta(LocalDateTime.now(), this, tipo);
        alerta.darDeAlta();
    }

    @Override
    public void reportarTemperatura() {
        reportarAlerta(Alerta.TipoAlerta.TEMPERATURA);
    }

    @Override
    public void reportarFraude() {
        reportarAlerta(Alerta.TipoAlerta.FRAUDE);
    }

    @Override
    public void reportarFallaConexion() {
        reportarAlerta(Alerta.TipoAlerta.FALLA_CONEXION);
    }
}
