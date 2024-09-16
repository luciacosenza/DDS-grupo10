package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import lombok.Getter;

@Getter
public abstract class AccionHeladera {
    protected final LocalDateTime fecha;
    protected final Heladera heladera;

    protected AccionHeladera(LocalDateTime vFecha, Heladera vHeladera) {
        fecha = vFecha;
        heladera = vHeladera;
    }

    public void darDeAlta() {
        Sistema.agregarAccionHeladera(this);
    }

    public void darDeBaja() {
        Sistema.eliminarAccionHeladera(this);
    }
}
