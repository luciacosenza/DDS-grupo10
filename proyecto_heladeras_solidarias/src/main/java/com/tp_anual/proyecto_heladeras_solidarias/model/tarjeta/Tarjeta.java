package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
public abstract class Tarjeta {
    
    @Id
    protected String codigo;

    protected Tarjeta() {}

    protected Tarjeta(String vCodigo) {
        codigo = vCodigo;
    }
}