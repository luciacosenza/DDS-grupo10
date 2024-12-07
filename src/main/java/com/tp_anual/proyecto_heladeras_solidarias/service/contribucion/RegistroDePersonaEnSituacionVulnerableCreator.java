package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion.DatosInvalidosCrearRPESVException;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class RegistroDePersonaEnSituacionVulnerableCreator implements ContribucionCreator {

    public RegistroDePersonaEnSituacionVulnerableCreator() {}

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        RegistroDePersonaEnSituacionVulnerable registroDePersonaEnSituacionVulnerable = new com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, new TarjetaPersonaEnSituacionVulnerable("", null));
        registroDePersonaEnSituacionVulnerable.seCompletoYSumoPuntos(); // Llamo directamente al método de registroDePersonaEnSituacionVulnerable, porque no quiero que se guarde en este momento

        return registroDePersonaEnSituacionVulnerable;
    }
    
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) throws DatosInvalidosCrearRPESVException {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);
        
        if(args.length != 1 ||
            !(args[0] instanceof TarjetaPersonaEnSituacionVulnerable)) {
            
            log.log(Level.SEVERE, I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerableCreator.crearContribucion_err"));
            throw new DatosInvalidosCrearRPESVException();
        }
        
        return new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, (TarjetaPersonaEnSituacionVulnerable) args[0]);
    }
}
