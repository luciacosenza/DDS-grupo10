package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AccionHeladera;

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

    @Transient
    @Getter(AccessLevel.NONE)
    protected final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    protected Tarjeta(String vCodigo) {
        codigo = vCodigo;
    }

    public abstract Boolean puedeUsar();

    public abstract AccionHeladera intentarApertura(HeladeraActiva heladeraAAbrir);
}