package com.tp_anual_dds.domain;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Heladera implements HeladeraObserver {
    private String nombre;
    private Ubicacion ubicacion;
    private ArrayList<Vianda> viandas;
    private Integer capacidad;
    private LocalDateTime fechaApertura;
    private Float tempMin;
    private Float tempMax;
    private Float tempActual;
    private Boolean activa;


    public Heladera(String vNombre, Ubicacion vUbicacion, ArrayList<Vianda> vViandas, Integer vCapacidad, LocalDateTime vFechaApertura, Float vTempMin, Float vTempMax) {
        nombre = vNombre;
        ubicacion = vUbicacion;
        viandas = vViandas;
        capacidad = vCapacidad;
        fechaApertura = vFechaApertura;
        tempMin = vTempMin;
        tempMax = vTempMax;
        tempActual = 0f;
        activa = true;
    }

    public ArrayList<Vianda> getViandas() {
        return viandas;
    }

    public Boolean estaActiva() {
        return activa;
    }

    public void actualizarEstado(Boolean nuevoEstado) {
        activa = nuevoEstado;
    }

    public Vianda retirarVianda() {
        return viandas.remove(0);
    }

    public void agregarVianda(Vianda vianda) {
        viandas.add(vianda);
    }

    // darDeAlta()
    // darDeBaja()
    // modificar()

    public void verificarTempActual() {
        if (tempActual < tempMin || tempActual > tempMax) {
            alertarTemperatura();
        }
    }

    @Override
    public void setTempActual(Float temperatura) {
        tempActual = temperatura;
        verificarTempActual();
    }

    public void alertarTemperatura() {
        System.out.println("La temperatura no está dentro de los parámetros correspondientes.");    // Esto es temporal, simula la notificacion a quienes corresponda, que seguramente sea responsabilidad de un Alertador (a implementar)
    }

    @Override
    public void alertarMovimiento(){
        System.out.println("LA HELADERA SE ESTÁ MOVIENDO.");    // Idem alertarTemperatura()
    }
}
