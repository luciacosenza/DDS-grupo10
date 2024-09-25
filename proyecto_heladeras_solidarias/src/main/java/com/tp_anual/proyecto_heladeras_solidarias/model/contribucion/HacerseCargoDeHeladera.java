package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
@Setter
public class HacerseCargoDeHeladera extends Contribucion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id; 

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "heladera_id")
    @NotNull
    private final HeladeraActiva heladeraObjetivo;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime ultimaActualizacion;

    @Transient
    private final Double multiplicadorPuntos = 5d;

    @Transient
    private final Integer periodoCalculoPuntos = 1;

    @Transient
    private final TimeUnit unidadPeriodoCalculoPuntos = TimeUnit.DAYS;

    @Transient
    @Getter(AccessLevel.NONE)
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public HacerseCargoDeHeladera(Colaborador vColaborador, LocalDateTime vFechaContribucion, HeladeraActiva vHeladeraObjetivo) {
        super(vColaborador, vFechaContribucion);
        heladeraObjetivo = vHeladeraObjetivo;
        ultimaActualizacion = LocalDateTime.now();
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.HacerseCargoDeHeladera.obtenerDetalles_out_heladera_objetivo", heladeraObjetivo.getNombre()));
    }

    @Override
    public void validarIdentidad() {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Double puntosSumados) {
        log.log(Level.INFO, I18n.getMessage("contribucion.HacerseCargoDeHeladera.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }
    
    @Override
    protected void calcularPuntos() {

        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            long mesesPasados = ChronoUnit.MONTHS.between(ultimaActualizacion, ahora);
            if (mesesPasados >= 1 && heladeraObjetivo.getEstado()) {    // Dado que en el test nos dimos cuenta que puede fallar por milésimas, podríamos pensar en restarle un segundo, por ejemplo, a meses pasados (TODO)
                Double puntosASumar = multiplicadorPuntos;
                colaborador.sumarPuntos(puntosASumar);
                confirmarSumaPuntos(puntosASumar);
                ultimaActualizacion = ahora;
            }
        };

        // Programa la tarea para que se ejecute una vez por día
        scheduler.scheduleAtFixedRate(calculoPuntos, 0, periodoCalculoPuntos, unidadPeriodoCalculoPuntos);  // Ejecuta una vez por día (puede ser ineficiente)
    }
}