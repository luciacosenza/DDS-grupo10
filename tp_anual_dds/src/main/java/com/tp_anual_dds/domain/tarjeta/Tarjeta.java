package com.tp_anual_dds.domain.tarjeta;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.AccionHeladera;

public abstract class Tarjeta {
    protected String codigo;
    protected final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
    protected final long timeout = 10;  // Tiempo (arbitrario) de espera de respuesta como Consumer
    protected final TimeUnit unit = TimeUnit.SECONDS; // Unidad de tiempo (arbitraria) de espera de respuesta como Consumer

    public abstract Object getTitular();

    public abstract Boolean puedeUsar();

    public abstract AccionHeladera intentarApertura(HeladeraActiva heladeraAAbrir)  throws InterruptedException;
}