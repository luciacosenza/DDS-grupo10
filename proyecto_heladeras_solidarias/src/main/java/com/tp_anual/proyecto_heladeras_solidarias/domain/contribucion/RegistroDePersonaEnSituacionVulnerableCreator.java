package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.message_loader.I18n;

public class RegistroDePersonaEnSituacionVulnerableCreator implements ContribucionCreator {
    private static final Logger logger = Logger.getLogger(RegistroDePersonaEnSituacionVulnerableCreator.class.getName());

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        return new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, new TarjetaPersonaEnSituacionVulnerable(null));
    }
    
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);
        
        if(args.length != 1 ||
            !(args[0] instanceof TarjetaPersonaEnSituacionVulnerable)) {
            
            logger.log(Level.SEVERE, I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerableCreator.crearContribucion_err"));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerableCreator.crearContribucion_exception"));
        }
        
        return new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, (TarjetaPersonaEnSituacionVulnerable) args[0]);
    }
}
