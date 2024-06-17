package com.tp_anual_dds.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HacerseCargoDeHeladera extends Contribucion {
    private Heladera heladeraObjetivo;
    private LocalDateTime ultimaActualizacion;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public HacerseCargoDeHeladera(Colaborador vColaborador, LocalDateTime vFechaContribucion, Heladera vHeladeraObjetivo) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        heladeraObjetivo = vHeladeraObjetivo;
        ultimaActualizacion = LocalDateTime.now();
    }

    // obtenerDetalles()
    
    @Override
    protected void validarIdentidad() {
        if(!esColaboradorJuridico(colaborador)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Jurídico");
        }
    }

    @Override
    protected void accionar() {
        System.out.println(heladeraObjetivo); // Esto es temporal, para que no tire errores. La logica es *registrar la heladera en el sistema*
    }

    @Override
    protected void calcularPuntos() {
        Double multiplicadorPuntos = 5d;
        
        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            long mesesPasados = ChronoUnit.MONTHS.between(ultimaActualizacion, ahora);
            if (mesesPasados >= 1 && heladeraObjetivo.estaActiva()) {
                colaborador.sumarPuntos(multiplicadorPuntos);
                ultimaActualizacion = ahora;
            }
        };

        // Programa la tarea para que se ejecute una vez por dia
        scheduler.scheduleAtFixedRate(calculoPuntos, 0, 1, TimeUnit.DAYS);  // Ejecuta una vez por dia, puede ser ineficiente
    }
}