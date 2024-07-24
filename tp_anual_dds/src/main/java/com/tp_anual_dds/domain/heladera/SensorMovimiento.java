package com.tp_anual_dds.domain.heladera;

import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.domain.incidente.Alerta.TipoAlerta;

public class SensorMovimiento extends Sensor {
    private Boolean hayMovimiento;
    
    public SensorMovimiento(HeladeraActiva vHeladera) {
        heladera = vHeladera;
        hayMovimiento = false;
    }
    
    @Override
    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public Boolean getMovimiento() {
        return hayMovimiento;
    }

    public void setHayMovimiento(Boolean vHayMovimiento) {
        hayMovimiento = vHayMovimiento;
    }

    @Override
    public void notificarHeladera() {
        heladera.producirAlerta(TipoAlerta.FRAUDE);
    }
    
    @Override
    public void programarNotificacion() {
        Integer periodo = 5;
        TimeUnit unidad = TimeUnit.MINUTES;
        
        Runnable notificacionMovimiento = () -> {
            if(hayMovimiento == true) {
                notificarHeladera();
            }
        };

        // Programa la tarea para que se ejecute cada 5 minutos
        scheduler.scheduleAtFixedRate(notificacionMovimiento, 0, periodo, unidad); // En este caso, hicimos que ejecute cada 5 minutos como ejemplo
    }

    // TODO Esto en sí no hace nada útil, faltaría la vinculación con el Sensor físico
}
