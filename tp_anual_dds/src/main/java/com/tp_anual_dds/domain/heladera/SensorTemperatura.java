package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.domain.incidente.Alerta.TipoAlerta;

public class SensorTemperatura extends Sensor {
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