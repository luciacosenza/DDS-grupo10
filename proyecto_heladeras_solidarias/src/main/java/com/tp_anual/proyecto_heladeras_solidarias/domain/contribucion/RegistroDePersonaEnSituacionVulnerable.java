package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class RegistroDePersonaEnSituacionVulnerable extends Contribucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tarjeta_persona_en_situacion_vulnerable_id")
    private final TarjetaPersonaEnSituacionVulnerable tarjetaAsignada;
    
    @Transient
    private final Double multiplicador_puntos = 2d;
    
    public RegistroDePersonaEnSituacionVulnerable(Colaborador vColaborador, LocalDateTime vFechaContribucion, TarjetaPersonaEnSituacionVulnerable vTarjetaAsignada) {
        super(vColaborador, vFechaContribucion);
        tarjetaAsignada = vTarjetaAsignada;
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.obtenerDetalles_out_tarjeta_asignada_titular", tarjetaAsignada.getTitular()));
    }
    
    @Override
    public void validarIdentidad() {
        if(colaborador.getDomicilio() == null) {
            log.log(Level.SEVERE, I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.validarIdentidad_exception"));
        }   
    }

    @Override
    protected void confirmarSumaPuntos(Double puntosSumados) {
        tarjetaAsignada.programarReseteoUsos();
        log.log(Level.INFO, I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos() {
        Double puntosASumar = multiplicador_puntos;
        colaborador.sumarPuntos(puntosASumar);
        confirmarSumaPuntos(puntosASumar);
    }
}