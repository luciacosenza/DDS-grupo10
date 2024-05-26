package domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SensorMovimiento extends Sensor {
    private Boolean hayMovimiento;
    
    public SensorMovimiento(Heladera vHeladera) {
        heladera = vHeladera;
        hayMovimiento = false;
    }
    
    public void agregar(Heladera observer) {
        heladera = observer;
    }

    public void eliminar(Heladera observer) {
        heladera = null;
    }
    
    public void notificar() {
        heladera.alertarMovimiento();
    }
    
    public void programarNotificacion() {
        Runnable notificacionMovimiento = () -> {
            if(hayMovimiento == true) {
                notificar();
            }
        };

        // Programa la tarea para que se ejecute cada 5 minutos
        scheduler.scheduleAtFixedRate(notificacionMovimiento, 0, 5, TimeUnit.MINUTES); // En este caso, hicimos que ejecute cada 5 minutos como ejemplo
    }

    // Esto en si no hace nada util, faltaria la vinculacion con el sensor fisico
}
