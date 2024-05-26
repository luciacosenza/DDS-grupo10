package domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Sensor implements SensorSubject {
    protected Heladera heladera;
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void agregar(Heladera observer) {
        heladera = observer;
    }

    public void eliminar(Heladera observer){
        heladera = null;
    }

    public abstract void notificar();
    public abstract void programarNotificacion();
}
