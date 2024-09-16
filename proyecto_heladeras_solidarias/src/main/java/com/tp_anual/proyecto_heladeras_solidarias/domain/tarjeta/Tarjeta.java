package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AccionHeladera;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public abstract class Tarjeta {
    protected final String codigo;

    protected Tarjeta(String vCodigo) {
        codigo = vCodigo;
    }

    @Getter(AccessLevel.NONE)
    protected final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    public abstract Boolean puedeUsar();

    public abstract AccionHeladera intentarApertura(HeladeraActiva heladeraAAbrir)  throws InterruptedException;
}