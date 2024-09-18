package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import lombok.AccessLevel;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_sensor")
@Getter
public abstract class Sensor implements SensorSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    protected HeladeraActiva heladera;

    @Transient
    @Getter(AccessLevel.NONE)
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    protected Sensor(HeladeraActiva vHeladera) {
        heladera = vHeladera;
    }

    public void darDeAlta() {
        Sistema.agregarSensor(this);
    }

    public void darDeBaja() {
        Sistema.eliminarSensor(this);
    }

    @Override
    public abstract void notificarHeladera();
}
