package com.tp_anual_dds.domain.tarjeta;

public abstract class Tarjeta {
    protected String codigo;

    public abstract Object getTitular();

    public abstract Boolean puedeUsar();
}