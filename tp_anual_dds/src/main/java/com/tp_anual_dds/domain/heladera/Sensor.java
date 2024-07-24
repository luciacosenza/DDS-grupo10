package com.tp_anual_dds.domain.heladera;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual_dds.sistema.Sistema;

public abstract class Sensor implements SensorSubject {
    protected HeladeraActiva heladera;
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public abstract HeladeraActiva getHeladera();

    public void darDeAlta() {
        Sistema.agregarSensor(this);
    }

    public void darDeBaja() {
        Sistema.eliminarSensor(this);
    }

    @Override
    public abstract void notificarHeladera();
    
    public abstract void programarNotificacion();
}
