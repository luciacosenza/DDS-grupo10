package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@NamedNativeQuery(
    name = "DonacionDinero.findDonacionDineroParaPuntos",
    query = "SELECT * FROM donacion_dinero AS d " +
            "WHERE (d.ya_sumo_puntos = false AND d.frecuencia = 'UNICA_VEZ') " +
            "OR (d.frecuencia = 'SEMANAL' AND CURRENT_DATE - d.ultima_actualizacion >= 7) " +
            "OR (d.frecuencia = 'MENSUAL' AND CURRENT_DATE - d.ultima_actualizacion >= 30) " +
            "OR (d.frecuencia = 'SEMESTRAL' AND CURRENT_DATE - d.ultima_actualizacion >= 180) " +
            "OR (d.frecuencia = 'ANUAL' AND CURRENT_DATE - d.ultima_actualizacion >= 365)"
)
@Log
@Getter
public class DonacionDinero extends Contribucion {
    
    @Min(value = 0)
    private Double monto;   // final

    @Enumerated(EnumType.STRING)
    @Min(value = 0)
    private FrecuenciaDePago frecuencia;    // final

    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private LocalDateTime ultimaActualizacion;

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

    public DonacionDinero() {
        super();
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
}
