package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
@Setter
public class SensorTemperatura extends Sensor {

    private Float tempActual;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ultimaActualizacion;

    private final Integer periodoNotificacionFalla = 5;

    private final TimeUnit unidadPeriodoNotificacionFalla = TimeUnit.MINUTES;

    private final long minutosPasadosMaximos = 5;   // Seteamos el tiempo (arbitrario) máximo que puede pasar sin recibir la temperatura del Sensor físico para mandar una Alerta

    public SensorTemperatura(HeladeraActiva vHeladera) {
        super(vHeladera);
        tempActual = 0f;
        ultimaActualizacion = LocalDateTime.now();
    }

    public void actualizarUltimaActualizacion() {
        setUltimaActualizacion(LocalDateTime.now());
    }
}