package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import lombok.Getter;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class SensorMovimiento extends Sensor {

    @Setter
    private Boolean hayMovimiento;

    public SensorMovimiento() {
        super();
    }

    public SensorMovimiento(Heladera vHeladera) {
        super(vHeladera);
        hayMovimiento = false;
    }
}