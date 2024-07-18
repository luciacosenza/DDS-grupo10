package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.incidentes.Alerta;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class Heladera implements HeladeraObserver {
    private String nombre;
    private Ubicacion ubicacion;
    private ArrayList<Vianda> viandas;
    private Integer capacidad;
    private LocalDateTime fechaApertura;
    private Float tempMin;
    private Float tempMax;
    private Float tempActual;
    private Boolean estado;


    public Heladera(String vNombre, Ubicacion vUbicacion, ArrayList<Vianda> vViandas, Integer vCapacidad, LocalDateTime vFechaApertura, Float vTempMin, Float vTempMax) {
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

    public ArrayList<Vianda> getViandas() {
        return viandas;
    }

    public Float getTempActual() {
        return tempActual;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean nuevoEstado) {
        estado = nuevoEstado;
    }

    public Vianda retirarVianda() {
        return viandas.remove(0);
    }

    public void agregarVianda(Vianda vianda) {
        viandas.add(vianda);
    }

    public void darDeAlta() {
        Sistema.agregarHeladera(this);
    }

    public void darDeBaja() {
        Sistema.eliminarHeladera(this);
    }

    public void verificarTempActual() {
        if (tempActual < tempMin || tempActual > tempMax) {
            reportarTemperatura();
        }
    }

    @Override
    public void setTempActual(Float temperatura) {
        tempActual = temperatura;
        verificarTempActual();
    }

    public void reportarAlerta(Alerta.TipoAlerta tipo) {
        setEstado(false);

        Alerta alerta = new Alerta(tipo);
        alerta.darDeAlta();
    }

    public void reportarTemperatura() {
        reportarAlerta(Alerta.TipoAlerta.TEMPERATURA);
    }

    @Override
    public void reportarFraude() {
        reportarAlerta(Alerta.TipoAlerta.FRAUDE);
    }

    public void reportarFallaConexion() {
        reportarAlerta(Alerta.TipoAlerta.FALLA_CONEXION);
    }
}
