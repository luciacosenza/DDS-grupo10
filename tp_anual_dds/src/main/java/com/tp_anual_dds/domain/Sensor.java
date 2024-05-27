package com.tp_anual_dds.domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Sensor implements SensorSubject {
    protected Heladera heladera;
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void agregar(Heladera observer) {
        heladera = observer;
    }

    @Override
    public void eliminar(Heladera observer){
        heladera = null;
    }

    @Override
    public abstract void notificar();
    
    public abstract void programarNotificacion();
}
