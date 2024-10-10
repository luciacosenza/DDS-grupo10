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
}