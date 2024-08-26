package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Alerta.TipoAlerta;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class SensorTemperatura extends Sensor {
    private static final Logger logger = Logger.getLogger(SensorTemperatura.class.getName());
    private Float tempActual;
    private LocalDateTime ultimaActualizacion;
    private final long minutosPasadosMaximos = 5;   // Seteamos el tiempo (arbitrario) máximo que puede pasar sin recibir la temperatura del Sensor Físico para mandar una Alerta

    public SensorTemperatura(HeladeraActiva vHeladera) {
        heladera = vHeladera;
        tempActual = 0f;
        ultimaActualizacion = LocalDateTime.now();
    }
    
    @Override
    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public Float getTempActual() {
        return tempActual;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public long getMinutosPasadosMaximos() {
        return minutosPasadosMaximos;
    }

    public void setTempActual(Float vTempActual) {
        tempActual = vTempActual;
        actualizarUltimaActualizacion();
    }

    public void setUltimaActualizacion(LocalDateTime vUltimaActualizacion) {
        ultimaActualizacion = vUltimaActualizacion;
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
        logger.log(Level.INFO, I18n.getMessage("heladera.SensorTemperatura.notificarHeladera_info", heladera.getNombre()));
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