package com.tp_anual.proyecto_heladeras_solidarias.model.persona;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected Persona() {}

    public abstract String getNombre(Integer n);

    public String getTipoPersona() {
        return getClass().getSimpleName();
    }

    public abstract void obtenerDetalles();
}