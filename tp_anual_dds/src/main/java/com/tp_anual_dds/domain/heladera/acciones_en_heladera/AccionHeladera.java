package com.tp_anual_dds.domain.heladera.acciones_en_heladera;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.sistema.Sistema;

public abstract class AccionHeladera {
    protected LocalDateTime fecha;
    protected Heladera heladera;

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Heladera getHeladera() {
        return heladera;
    }

    public void darDeAlta() {
        Sistema.agregarAccionHeladera(this);
    }

    public void darDeBaja() {
        Sistema.eliminarAccionHeladera(this);
    }
}
