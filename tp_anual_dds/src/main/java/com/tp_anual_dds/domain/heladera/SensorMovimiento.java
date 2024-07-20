package com.tp_anual_dds.domain.heladera;

import java.util.concurrent.TimeUnit;

public class SensorMovimiento extends Sensor {
    private Boolean hayMovimiento;
    
    public SensorMovimiento(HeladeraActiva vHeladera) {
        heladera = vHeladera;
        hayMovimiento = false;
    }
    
    public void setHayMovimiento(Boolean vHayMovimiento) {
        hayMovimiento = vHayMovimiento;
    }

    @Override
    public void notificar() {
        heladera.reportarFraude();
    }
    
    @Override
    public void programarNotificacion() {
        Integer periodo = 5;
        TimeUnit unidad = TimeUnit.MINUTES;
        
        Runnable notificacionMovimiento = () -> {
            if(hayMovimiento == true) {
                notificar();
            }
        };

        // Programa la tarea para que se ejecute cada 5 minutos
        scheduler.scheduleAtFixedRate(notificacionMovimiento, 0, periodo, unidad); // En este caso, hicimos que ejecute cada 5 minutos como ejemplo
    }

    // Esto en si no hace nada util, faltaria la vinculacion con el sensor fisico
}
