package com.tp_anual_dds.domain.heladera;

import java.util.concurrent.TimeUnit;

public class SensorTemperatura extends Sensor {
    private Float tempActual;

    public SensorTemperatura(HeladeraActiva vHeladera) {
        heladera = vHeladera;
        tempActual = 0f;
    }

    public void setTempActual(Float vTempActual) {
        tempActual = vTempActual;
    }

    public Boolean funcionaSensorFisico() {
        return true;    // Simulamos el buen funcionamiento del SensorFisico
    }

    @Override
    public void notificarHeladera() {
        heladera.setTempActual(tempActual);
    }

    public void notificarFalla() {
        heladera.reportarFallaConexion();
    }
    
    @Override
    public void programarNotificacion() {
        Integer periodo = 10;
        TimeUnit unidad = TimeUnit.MINUTES;
        
        Runnable notificacionTemperatura = () -> {
            if (!funcionaSensorFisico()) {
                notificarFalla();
            }
            notificarHeladera();
        };
        
        // Programa la tarea para que se ejecute cada 5 minutos
        scheduler.scheduleAtFixedRate(notificacionTemperatura, 0, periodo, unidad);
    }

    // Esto en si no hace nada util, faltaria la vinculacion con el sensor fisico
}