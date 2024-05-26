package domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SensorTemperatura extends Sensor {
    private Float tempActual;

    public SensorTemperatura(Heladera vHeladera) {
        heladera = vHeladera;
        tempActual = 0f;
    }
    
    public void agregar(Heladera observer) {
        heladera = observer;
    }

    public void eliminar(Heladera observer) {
        heladera = null;
    }

    public void notificar() {
        heladera.setTempActual(tempActual);
    }
    
    public void programarNotificacion() {
        Runnable notificacionTemperatura = () -> {
            notificar();
        };

        // Programa la tarea para que se ejecute cada 5 minutos
        scheduler.scheduleAtFixedRate(notificacionTemperatura, 0, 5, TimeUnit.MINUTES);
    }

    // Esto en si no hace nada util, faltaria la vinculacion con el sensor fisico
}