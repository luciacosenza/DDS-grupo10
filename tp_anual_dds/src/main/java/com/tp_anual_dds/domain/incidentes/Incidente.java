package com.tp_anual_dds.domain.incidentes;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.Heladera;

public abstract class Incidente {
    protected LocalDateTime fecha;
    protected Heladera heladera;

    public Heladera getHeladera() {
        return heladera;
    }
}
