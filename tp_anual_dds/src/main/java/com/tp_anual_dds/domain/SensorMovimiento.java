package com.tp_anual_dds.domain;

import java.util.concurrent.TimeUnit;

public class SensorMovimiento extends Sensor {
    private Boolean hayMovimiento;
    
    public SensorMovimiento(Heladera vHeladera) {
        heladera = vHeladera;
        hayMovimiento = false;
    }
    
    @Override
    public void notificar() {
        heladera.alertarMovimiento();
    }
    
    @Override
    public void programarNotificacion() {
        Runnable notificacionMovimiento = () -> {
            if(hayMovimiento == true) {
                notificar();
            }
        };

        // Programa la tarea para que se ejecute cada 5 minutos
        scheduler.scheduleAtFixedRate(notificacionMovimiento, 0, 5, TimeUnit.MINUTES); // En este caso, hicimos que ejecute cada 5 minutos como ejemplo
    }

    // Esto en si no hace nada util, faltaria la vinculacion con el sensor fisico
}
