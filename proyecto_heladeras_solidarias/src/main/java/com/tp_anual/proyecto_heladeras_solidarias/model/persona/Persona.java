package com.tp_anual.proyecto_heladeras_solidarias.model.persona;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public abstract String getNombre(Integer n);

    public String getTipoPersona() {
        return getClass().getSimpleName();
    }

    public abstract void obtenerDetalles();
}