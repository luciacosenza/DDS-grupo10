package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import lombok.AccessLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Log
@Getter
@Setter
public abstract class Sensor implements SensorSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    @NotNull
    protected HeladeraActiva heladera;
    
    protected Sensor(HeladeraActiva vHeladera) {
        heladera = vHeladera;
    }

    public void darDeAlta() {
        Sistema.agregarSensor(this);
    }

    public void darDeBaja() {
        Sistema.eliminarSensor(this);
    }
}
