package com.tp_anual_dds.domain.tarjeta;

import java.util.ArrayList;

public abstract class Tarjeta {
    protected String codigo;
    protected ArrayList<UsoTarjeta> usos;

    public void agregarUso(UsoTarjeta uso) {
        usos.add(uso);
    }

    public void resetUsos() {
        usos.clear();
    }

    public Integer cantidadUsos() {
        return usos.size();
    }

    public abstract Boolean puedeUsar();

    // usar()
}