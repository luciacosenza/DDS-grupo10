package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AccionHeladera;

public abstract class Tarjeta {
    protected String codigo;
    protected final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    public abstract Object getTitular();

    public abstract Boolean puedeUsar();

    public abstract AccionHeladera intentarApertura(HeladeraActiva heladeraAAbrir)  throws InterruptedException;
}