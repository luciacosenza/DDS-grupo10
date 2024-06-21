package com.tp_anual_dds.domain;

import java.util.ArrayList;

public abstract class Tarjeta {
    protected String codigo;
    protected ArrayList<UsoTarjeta> usos;
    private PersonaEnSituacionVulnerable titular;

    public PersonaEnSituacionVulnerable getTitular() {
        return titular;
    }

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