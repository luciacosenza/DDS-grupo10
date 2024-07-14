package com.tp_anual_dds.domain.heladera;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Sensor implements SensorSubject {
    protected Heladera heladera;
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public abstract void notificar();
    
    public abstract void programarNotificacion();
}
