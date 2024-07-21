package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class HacerseCargoDeHeladera extends Contribucion {
    private HeladeraActiva heladeraObjetivo;
    private LocalDateTime ultimaActualizacion;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Double multiplicador_puntos = 5d;

    public HacerseCargoDeHeladera(Colaborador vColaborador, LocalDateTime vFechaContribucion, HeladeraActiva vHeladeraObjetivo) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        heladeraObjetivo = vHeladeraObjetivo;
        ultimaActualizacion = LocalDateTime.now();
        completada = false;
    }

    // obtenerDetalles()
    
    @Override
    public void validarIdentidad() {}

    @Override
    protected void calcularPuntos() {
        Integer periodo = 1;
        TimeUnit unidad = TimeUnit.DAYS;
        
        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            long mesesPasados = ChronoUnit.MONTHS.between(ultimaActualizacion, ahora);
            if (mesesPasados >= 1 && heladeraObjetivo.getEstado()) {    // Dado que en el test nos dimos cuenta que puede fallar por milesimas, podriamos pensar en restarle un segundo, por ejemplo, a mesesPasados
                colaborador.sumarPuntos(multiplicador_puntos);
                ultimaActualizacion = ahora;
            }
        };

        // Programa la tarea para que se ejecute una vez por dia
        scheduler.scheduleAtFixedRate(calculoPuntos, 0, periodo, unidad);  // Ejecuta una vez por dia, puede ser ineficiente
    }
}