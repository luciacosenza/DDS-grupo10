package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;

import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class SensorTemperatura extends Sensor {

    @Setter
    private Float tempActual;

    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private LocalDateTime ultimaActualizacion;

    public SensorTemperatura() {
        super();
    }

    public SensorTemperatura(Heladera vHeladera) {
        super(vHeladera);
        tempActual = 0f;
        ultimaActualizacion = LocalDateTime.now();
    }

    public void actualizarUltimaActualizacion() {
        setUltimaActualizacion(LocalDateTime.now());
    }
}