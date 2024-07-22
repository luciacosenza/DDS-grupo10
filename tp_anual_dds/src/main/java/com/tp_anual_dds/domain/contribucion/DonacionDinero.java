package com.tp_anual_dds.domain.contribucion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.domain.colaborador.Colaborador;

public class DonacionDinero extends Contribucion {
    private final Double monto;
    private final FrecuenciaDePago frecuencia;
    private LocalDateTime ultimaActualizacion;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Double multiplicador_puntos = 0.5;
    
    public enum FrecuenciaDePago {
        SEMANAL {
            @Override
            public Integer periodo() {
                return 1;
            }
    
            @Override
            public ChronoUnit unidad() {
                return ChronoUnit.WEEKS;
            }
        },
        MENSUAL {
            @Override
            public Integer periodo() {
                return 1;
            }
    
            @Override
            public ChronoUnit unidad() {
                return ChronoUnit.MONTHS;
            }
        },
        SEMESTRAL {
            @Override
            public Integer periodo() {
                return 6;
            }
    
            @Override
            public ChronoUnit unidad() {
                return ChronoUnit.MONTHS;
            }
        },
        ANUAL {
            @Override
            public Integer periodo() {
                return 1;
            }
    
            @Override
            public ChronoUnit unidad() {
                return ChronoUnit.YEARS;
            }
        },
        UNICA_VEZ {
            @Override
            public Integer periodo() {
                return 0;
            }
    
            @Override
            public ChronoUnit unidad() {
                return null;
            }
        };
    
        public abstract Integer periodo();
        public abstract ChronoUnit unidad();
    }

    public DonacionDinero(Colaborador vColaborador, LocalDateTime vFechaContribucion, Double vMonto, FrecuenciaDePago vFrecuencia) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        monto = vMonto;
        frecuencia = vFrecuencia;
        ultimaActualizacion = LocalDateTime.now();
        completada = false;
    }

    public Double getMonto() {
        return monto;
    }

    public FrecuenciaDePago getFrecuencia() {
        return frecuencia;
    }

    // obtenerDetalles()
    
    @Override
    public void validarIdentidad() {}

    @Override
    protected void calcularPuntos() {    
        if (frecuencia == FrecuenciaDePago.UNICA_VEZ) {
            colaborador.sumarPuntos(monto * multiplicador_puntos);
            return; // Corta la ejecucion del metodo
        } 

        Integer periodo = 1;
        TimeUnit unidad = TimeUnit.DAYS;

        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            Long periodosPasados = frecuencia.unidad().between(ultimaActualizacion, ahora);
            if (periodosPasados >= frecuencia.periodo()) {      // Dado que en el test nos dimos cuenta que puede fallar por milesimas, podriamos pensar en restarle un segundo, por ejemplo, a periodosPasadosos
                colaborador.sumarPuntos(monto * multiplicador_puntos);
                ultimaActualizacion = ahora;
            }
        };

        // Programa la tarea para que se ejecute una vez por dia
        scheduler.scheduleAtFixedRate(calculoPuntos, 0, periodo, unidad);  // Ejecuta una vez por dia, puede ser ineficiente en casos como MENSUAL, SEMESTRAL o ANUAL
    }
}
