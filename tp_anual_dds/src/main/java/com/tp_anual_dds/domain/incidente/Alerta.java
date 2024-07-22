package com.tp_anual_dds.domain.incidente;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class Alerta extends Incidente {
    private final TipoAlerta tipo;

    public enum TipoAlerta {
        TEMPERATURA,
        FRAUDE,
        FALLA_CONEXION
    }

    public Alerta(LocalDateTime vFecha, HeladeraActiva vHeladera, TipoAlerta vTipo) {
        fecha = vFecha;
        heladera = vHeladera;
        tipo = vTipo;
    }

    public TipoAlerta getTipo() {
        return tipo;
    }
}
