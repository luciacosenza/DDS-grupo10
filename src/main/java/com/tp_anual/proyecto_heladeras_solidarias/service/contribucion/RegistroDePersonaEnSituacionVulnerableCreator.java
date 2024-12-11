package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion.DatosInvalidosCrearRPESVException;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class RegistroDePersonaEnSituacionVulnerableCreator implements ContribucionCreator {

    private final I18nService i18nService;

    public RegistroDePersonaEnSituacionVulnerableCreator(I18nService vI18nService) {
        i18nService = vI18nService;
    }

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        RegistroDePersonaEnSituacionVulnerable registroDePersonaEnSituacionVulnerable = new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, null);
        registroDePersonaEnSituacionVulnerable.seCompletoYSumoPuntos(); // Llamo directamente al método de registroDePersonaEnSituacionVulnerable, porque no quiero que se guarde en este momento

        return registroDePersonaEnSituacionVulnerable;
    }
    
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) throws DatosInvalidosCrearRPESVException {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);
        
        if(args.length != 1 ||
            !(args[0] instanceof TarjetaPersonaEnSituacionVulnerable)) {
            
            log.log(Level.SEVERE, i18nService.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerableCreator.crearContribucion_err"));
            throw new DatosInvalidosCrearRPESVException();
        }
        
        return new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, (TarjetaPersonaEnSituacionVulnerable) args[0]);
    }
}
