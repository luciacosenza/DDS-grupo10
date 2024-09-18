package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AccionHeladera;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
public abstract class Tarjeta {
    
    @Id
    protected final String codigo;    

    @Transient
    @Getter(AccessLevel.NONE)
    protected final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    protected Tarjeta(String vCodigo) {
        codigo = vCodigo;
    }

    public abstract Boolean puedeUsar();

    public abstract AccionHeladera intentarApertura(HeladeraActiva heladeraAAbrir)  throws InterruptedException;
}