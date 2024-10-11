package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Tarjeta {
    
    @Id
    @Setter(AccessLevel.NONE)
    protected String codigo;

    protected Tarjeta(String vCodigo) {
        codigo = vCodigo;
    }

    public abstract Boolean puedeUsar();
}