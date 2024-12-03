package com.tp_anual.proyecto_heladeras_solidarias.model.heladera.sensor;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Log
@Getter
public abstract class Sensor implements SensorSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera")
    @Setter
    protected Heladera heladera;

    protected Sensor() {}

    protected Sensor(Heladera vHeladera) {
        heladera = vHeladera;
    }
}
