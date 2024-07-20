package com.tp_anual_dds.domain.tarjeta;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public abstract class Tarjeta {
    protected String codigo;
    protected final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    public abstract Object getTitular();

    public abstract Boolean puedeUsar();

    public abstract void intentarApertura(HeladeraActiva heladeraAAbrir);
}