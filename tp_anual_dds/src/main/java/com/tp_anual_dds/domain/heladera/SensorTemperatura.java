package com.tp_anual_dds.domain.heladera;

import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.broker.MensajeTemperatura;
import com.tp_anual_dds.domain.incidente.Alerta.TipoAlerta;
import com.tp_anual_dds.sistema.Sistema;

public class SensorTemperatura extends Sensor {
    private Float tempActual;

    public SensorTemperatura(HeladeraActiva vHeladera) {
        heladera = vHeladera;
        tempActual = 0f;
    }
    
    @Override
    public HeladeraActiva getHeladera() {
        return heladera;
    }
    
    public Float getTempActual() {
        return tempActual;
    }

    public void setTempActual(Float vTempActual) {
        tempActual = vTempActual;
    }

    public Boolean funcionaSensorFisico() {
        return true;    // TODO Suponemos el buen funcionamiento del Sensor físico (esto debería ser modificado si se implementara la conexión con el mismo)
    }

    @Override
    public void notificarHeladera() {
        MensajeTemperatura mensajeTemperatura = new MensajeTemperatura(heladera, tempActual);
        
        new Thread( () -> {
            try {
                Sistema.getBroker().agregarMensaje(mensajeTemperatura);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("El hilo fue interrumpido: " + e.getMessage()); // TODO Chequear si está bien que lo tire en System.err
            }
        }).start();
    }

    public void notificarFalla() {
        heladera.producirAlerta(TipoAlerta.FALLA_CONEXION);
    }
    
    @Override
    public void programarNotificacion() {
        Integer periodo = 10;
        TimeUnit unidad = TimeUnit.MINUTES;
        
        Runnable notificacionTemperatura = () -> {
            if (!funcionaSensorFisico()) {
                notificarFalla();
            }
            notificarHeladera();
        };
        
        // Programa la tarea para que se ejecute cada 5 minutos
        scheduler.scheduleAtFixedRate(notificacionTemperatura, 0, periodo, unidad);
    }

    // TODO Esto en sí no hace nada útil, faltaría la vinculación con el Sensor físico
}