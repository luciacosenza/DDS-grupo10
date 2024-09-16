package com.tp_anual.proyecto_heladeras_solidarias.reporte;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Reporte {
    @Getter(AccessLevel.NONE)
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public abstract void programarReporte();
}