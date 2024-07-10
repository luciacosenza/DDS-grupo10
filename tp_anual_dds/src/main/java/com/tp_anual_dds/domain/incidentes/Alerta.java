package com.tp_anual_dds.domain.incidentes;

public class Alerta extends Incidente {
    private TipoAlerta tipo;

    public enum TipoAlerta {
        TEMPERATURA,
        FRAUDE,
        FALLA_CONEXION
    }

    public Alerta(TipoAlerta vTipo) {
        tipo = vTipo;
    }
}
