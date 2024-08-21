package com.tp_anual.proyecto_heladeras_solidarias.reporte;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Reporte {
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public abstract void programarReporte();
}