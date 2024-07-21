package com.tp_anual_dds.reporte;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Reporte {
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public abstract void programarReporte();
}