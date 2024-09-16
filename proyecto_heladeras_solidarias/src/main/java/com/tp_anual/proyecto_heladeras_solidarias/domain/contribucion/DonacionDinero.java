package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
public class DonacionDinero extends Contribucion {
    private final Double monto;
    private final FrecuenciaDePago frecuencia;
    private LocalDateTime ultimaActualizacion;
    private final Double multiplicador_puntos = 0.5;

    @Getter(AccessLevel.NONE)
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public enum FrecuenciaDePago {  // Hacemos que Frecuencia de Pago sea una "interfaz común" para las distintas frecuencias, brindando los métodos periodo() unidad() para el uso de polimorfismo
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
        super(vColaborador, vFechaContribucion);
        monto = vMonto;
        frecuencia = vFrecuencia;
        ultimaActualizacion = LocalDateTime.now();
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.DonacionDinero.obtenerDetalles_out_monto_frecuencia", monto, frecuencia));
    }

    @Override
    public void validarIdentidad() {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Double puntosSumados) {
        log.log(Level.INFO, I18n.getMessage("contribucion.DonacionDinero.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos() {    
        if (frecuencia == FrecuenciaDePago.UNICA_VEZ) {
            Double puntosASumar = monto * multiplicador_puntos;
            colaborador.sumarPuntos(puntosASumar);
            confirmarSumaPuntos(puntosASumar);
        } 

        Integer periodo = 1;
        TimeUnit unidad = TimeUnit.DAYS;

        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            Long periodosPasados = frecuencia.unidad().between(ultimaActualizacion, ahora);
            if (periodosPasados >= frecuencia.periodo()) {  // TODO: Dado que en el test nos dimos cuenta que puede fallar por milésimas, podríamos pensar en restarle un segundo, por ejemplo, a períodos pasados
                Double puntosASumarL = monto * multiplicador_puntos;
                colaborador.sumarPuntos(puntosASumarL);
                confirmarSumaPuntos(puntosASumarL);
                ultimaActualizacion = ahora;
            }
        };

        // Programa la tarea para que se ejecute una vez por día
        scheduler.scheduleAtFixedRate(calculoPuntos, 0, periodo, unidad);  // Ejecuta una vez por día (puede ser ineficiente en casos como mensual, semestral o anual)
    }
}
