package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
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
public class RegistroDePersonaEnSituacionVulnerable extends Contribucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tarjeta_persona_en_situacion_vulnerable_id")
    @NotNull
    private final TarjetaPersonaEnSituacionVulnerable tarjetaAsignada;
    
    public RegistroDePersonaEnSituacionVulnerable(Colaborador vColaborador, LocalDateTime vFechaContribucion, TarjetaPersonaEnSituacionVulnerable vTarjetaAsignada) {
        super(vColaborador, vFechaContribucion);
        tarjetaAsignada = vTarjetaAsignada;
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.obtenerDetalles_out_tarjeta_asignada_titular", tarjetaAsignada.getTitular()));
    }
}