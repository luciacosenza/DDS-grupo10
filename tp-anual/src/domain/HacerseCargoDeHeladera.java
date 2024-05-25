package domain;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HacerseCargoDeHeladera extends Contribucion {
    private Heladera heladeraObjetivo;
    private LocalDateTime ultimaActualizacion;

    public HacerseCargoDeHeladera(Colaborador vColaborador, LocalDateTime vFechaContribucion, Heladera vHeladeraObjetivo) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        heladeraObjetivo = vHeladeraObjetivo;
        ultimaActualizacion = LocalDateTime.now();
    }

    // obtenerDetalles()
    
    public void validarIdentidad(Colaborador colaboradorAspirante) {
        if(!esColaboradorJuridico(colaboradorAspirante)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Juridico");
        }
    }

    public void accionar() {
        System.out.println(heladeraObjetivo); // Esto es temporal, para que no tire errores. La logica es *registrar la heladera en el sistema*
    }

    public void calcularPuntos(Colaborador colaborador) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            long mesesPasados = ChronoUnit.MONTHS.between(ultimaActualizacion, ahora);
            if (mesesPasados >= 1 && heladeraObjetivo.estaActiva()) {
                colaborador.sumarPuntos(5d);
                ultimaActualizacion = ahora;
            }
        };

        // Programa la tarea para que se ejecute cada 1 dia
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.DAYS);
    }
}