package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class RegistroDePersonaEnSituacionVulnerableCreator implements ContribucionCreator {
    
    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        return new com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, new TarjetaPersonaEnSituacionVulnerable(null));
    }
    
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);
        
        if(args.length != 1 ||
            !(args[0] instanceof TarjetaPersonaEnSituacionVulnerable)) {
            
            log.log(Level.SEVERE, I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerableCreator.crearContribucion_err"));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerableCreator.crearContribucion_exception"));
        }
        
        return new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, (TarjetaPersonaEnSituacionVulnerable) args[0]);
    }
}
