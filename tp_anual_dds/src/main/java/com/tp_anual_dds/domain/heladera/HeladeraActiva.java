package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.incidentes.Alerta;
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
    public ArrayList<Vianda> getViandas() {
        return viandas;
    }

    @Override
    public Integer getCapacidad() {
        return capacidad;
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
    public Vianda retirarVianda() {
        return viandas.remove(0);
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
    public Boolean verificarCapacidad() {
        return viandas.size() < capacidad;
    }

    @Override
    public void agregarVianda(Vianda vianda) {
        if (verificarCapacidad()) {
            viandas.add(vianda);
        } else {
            throw new IllegalStateException("No se puede agregar la vianda. Se superaría la capacidad de la heladera.");
        }
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
    public void reportarAlerta(Alerta.TipoAlerta tipo) {
        setEstado(false);

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
