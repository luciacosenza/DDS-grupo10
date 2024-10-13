package com.tp_anual.proyecto_heladeras_solidarias.model.persona;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;

    public Persona() {}

    public abstract String getNombre(Integer n);

    public String getTipoPersona() {
        return getClass().getSimpleName();
    }

    public abstract void obtenerDetalles();
}