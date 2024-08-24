package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;

public class RegistroDePersonaEnSituacionVulnerableCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        return new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, new TarjetaPersonaEnSituacionVulnerable(null));
    }
    
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);
        
        if(args.length != 1 ||
            !(args[0] instanceof TarjetaPersonaEnSituacionVulnerable))
            
            throw new IllegalArgumentException("Datos inválidos para registrar una persona en situación vulnerable");
        
        return new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, (TarjetaPersonaEnSituacionVulnerable) args[0]);
    }
}
