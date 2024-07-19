package com.tp_anual_dds.domain.incidentes;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.sistema.Sistema;

public abstract class Incidente {
    protected LocalDateTime fecha;
    protected HeladeraActiva heladera;

    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public void darDeAlta() {
        Sistema.agregarIncidente(this);
    }

    public void darDeBaja() {
        Sistema.eliminarIncidente(this);
    }
}
