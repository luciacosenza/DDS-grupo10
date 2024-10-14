package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
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
    private final Heladera heladeraObjetivo;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ultimaActualizacion;

    public HacerseCargoDeHeladera(Colaborador vColaborador, LocalDateTime vFechaContribucion, Heladera vHeladeraObjetivo) {
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