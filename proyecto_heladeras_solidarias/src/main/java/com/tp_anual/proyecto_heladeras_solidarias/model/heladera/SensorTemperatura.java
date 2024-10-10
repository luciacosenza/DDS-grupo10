package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta.TipoAlerta;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.extern.java.Log;
import jakarta.validation.constraints.NotNull;

@Entity
@Log
@Getter
@Setter
public class SensorTemperatura extends Sensor {

    @NotNull
    private Float tempActual;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime ultimaActualizacion;

    @Transient
    Integer periodoNotificacionFalla = 5;

    @Transient
    TimeUnit unidadPeriodoNotificacionFalla = TimeUnit.MINUTES;

    @Transient
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