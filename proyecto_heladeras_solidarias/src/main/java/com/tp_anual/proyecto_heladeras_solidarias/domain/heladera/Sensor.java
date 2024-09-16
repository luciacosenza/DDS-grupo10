package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public abstract class Sensor implements SensorSubject {
    protected HeladeraActiva heladera;

    @Getter(AccessLevel.NONE)
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public void darDeAlta() {
        Sistema.agregarSensor(this);
    }

    public void darDeBaja() {
        Sistema.eliminarSensor(this);
    }

    @Override
    public abstract void notificarHeladera();
}
