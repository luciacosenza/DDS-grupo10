package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.Locale;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;

@Entity
@Log
@Getter
public class RegistroDePersonaEnSituacionVulnerable extends Contribucion {
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tarjeta_asignada")
    private TarjetaPersonaEnSituacionVulnerable tarjetaAsignada;    // final

    public RegistroDePersonaEnSituacionVulnerable() {
        super();
    }

    public RegistroDePersonaEnSituacionVulnerable(Colaborador vColaborador, LocalDateTime vFechaContribucion, TarjetaPersonaEnSituacionVulnerable vTarjetaAsignada) {
        super(vColaborador, vFechaContribucion);
        tarjetaAsignada = vTarjetaAsignada;
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();

        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String logMessage = messageSource.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.obtenerDetalles_out_tarjeta_asignada_titular", new Object[] {tarjetaAsignada.getTitular()}, Locale.getDefault());

        System.out.println(logMessage);
    }
}