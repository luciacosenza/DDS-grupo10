package com.tp_anual_dds.domain.heladera.acciones_en_heladera;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.sistema.Sistema;

public abstract class AccionHeladera {
    protected LocalDateTime fecha;
    protected HeladeraActiva heladera;
    protected Object responsable;

    public void darDeAlta() {
        Sistema.agregarAccionHeladera(this);
    }

    public void darDeBaja() {
        Sistema.eliminarAccionHeladera(this);
    }
}
