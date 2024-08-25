package com.tp_anual.proyecto_heladeras_solidarias.domain.persona;

public abstract class Persona {
    public abstract String getNombre(Integer n);

    public String getTipoPersona() {
        return getClass().getSimpleName();
    }

    public abstract void obtenerDetalles();
}