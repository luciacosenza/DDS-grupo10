package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import lombok.AccessLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Log
@Getter
@Setter
public abstract class Sensor implements SensorSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    protected Long id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera")
    protected Heladera heladera;
    
    protected Sensor(Heladera vHeladera) {
        heladera = vHeladera;
    }
}
