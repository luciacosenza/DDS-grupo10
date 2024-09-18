package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Alerta.TipoAlerta;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.extern.java.Log;

@Entity
@DiscriminatorValue("Temperatura")
@Log
@Getter
@Setter
public class SensorTemperatura extends Sensor {

    private Float tempActual;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ultimaActualizacion;

    @Transient
    private final long minutosPasadosMaximos = 5;   // Seteamos el tiempo (arbitrario) máximo que puede pasar sin recibir la temperatura del Sensor físico para mandar una Alerta

    public SensorTemperatura(HeladeraActiva vHeladera) {
        super(vHeladera);
        tempActual = 0f;
        ultimaActualizacion = LocalDateTime.now();
    }

    public void setTempActual(Float vTempActual) {
        tempActual = vTempActual;
        actualizarUltimaActualizacion();
    }

    public void actualizarUltimaActualizacion() {
        setUltimaActualizacion(LocalDateTime.now());
    }

    public Boolean funcionaSensorFisico() {
        LocalDateTime ahora = LocalDateTime.now();
        long minutosPasados = ChronoUnit.MINUTES.between(ultimaActualizacion, ahora);
        return minutosPasados <= minutosPasadosMaximos; // Si pasa más del tiempo estimado, se lanzará una Alerta
    }

    @Override
    public void notificarHeladera() {
        heladera.setTempActual(tempActual);
        log.log(Level.INFO, I18n.getMessage("heladera.SensorTemperatura.notificarHeladera_info", heladera.getNombre()));
    }

    public void notificarFalla() {
        heladera.producirAlerta(TipoAlerta.FALLA_CONEXION);
    }
    
    public void programarNotificacion() {
        Integer periodo = 5;
        TimeUnit unidad = TimeUnit.MINUTES;
        
        Runnable notificacionTemperatura = () -> {
            if (!funcionaSensorFisico())
                notificarFalla();
                
            notificarHeladera();
        };
        
        // Programa la tarea para que se ejecute cada 5 minutos
        scheduler.scheduleAtFixedRate(notificacionTemperatura, 0, periodo, unidad);
    }
}