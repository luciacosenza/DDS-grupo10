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
        return true;    // Suponemos el buen funcionamiento del Sensor físico (esto debería ser modificado si se implementara la conexión con el mismo) (TODO)(?
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

    // Esto en sí no hace nada útil, faltaría la vinculación con el Sensor físico (TODO)(?
}