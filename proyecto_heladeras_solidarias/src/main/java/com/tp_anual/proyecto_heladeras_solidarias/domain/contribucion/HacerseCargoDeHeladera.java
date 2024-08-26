package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class HacerseCargoDeHeladera extends Contribucion {
    private final HeladeraActiva heladeraObjetivo;
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

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.HacerseCargoDeHeladera.obtenerDetalles_out_heladera_objetivo", heladeraObjetivo.getNombre()));
    }
    
    public HeladeraActiva getHeladeraObjetivo() {
        return heladeraObjetivo;
    }

    @Override
    public void validarIdentidad() {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void calcularPuntos() {
        Integer periodo = 1;
        TimeUnit unidad = TimeUnit.DAYS;
        
        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            long mesesPasados = ChronoUnit.MONTHS.between(ultimaActualizacion, ahora);
            if (mesesPasados >= 1 && heladeraObjetivo.getEstado()) {    // Dado que en el test nos dimos cuenta que puede fallar por milésimas, podríamos pensar en restarle un segundo, por ejemplo, a meses pasados (TODO)
                colaborador.sumarPuntos(multiplicador_puntos);
                ultimaActualizacion = ahora;
            }
        };

        // Programa la tarea para que se ejecute una vez por día
        scheduler.scheduleAtFixedRate(calculoPuntos, 0, periodo, unidad);  // Ejecuta una vez por día (puede ser ineficiente)
    }
}