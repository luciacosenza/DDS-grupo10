package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DonacionDinero extends Contribucion {
    private Double monto;
    private FrecuenciaDePago frecuencia;
    private LocalDateTime ultimaActualizacion;
    
    enum FrecuenciaDePago {
        SEMANAL,
        MENSUAL,
        SEMESTRAL,
        ANUAL,
        UNICA_VEZ
    }

    public DonacionDinero(Colaborador vColaborador, LocalDateTime vFechaContribucion, Double vMonto, FrecuenciaDePago vFrecuencia) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        monto = vMonto;
        frecuencia = vFrecuencia;
        ultimaActualizacion = LocalDateTime.now();
    }

    // obtenerDetalles()
    
    public void validarIdentidad(Colaborador colaboradorAspirante) {
        if(!(esColaboradorHumano(colaboradorAspirante) || esColaboradorJuridico(colaboradorAspirante))) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Humano");
        }
    }

    public void accionar() {
        System.out.println(monto);
        System.out.println(frecuencia);
        // Esto es temporal, para que no tire errores. La logica es *registrar la donacion en el sistema*
    }

    public void calcularPuntos(Colaborador colaborador) {
        // Modelo de metodo suponiendo frecuencia "CADA_5_SEGUNDOS"
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            long segundosPasados = ChronoUnit.SECONDS.between(ultimaActualizacion, ahora);
            if (segundosPasados >= 5) {
                colaborador.sumarPuntos(0.5 * monto);
                ultimaActualizacion = ahora;
            }
        };

        // Programa la tarea para que se ejecute cada 5 segundos
        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }
}
