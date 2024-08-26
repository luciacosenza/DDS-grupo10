package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class TarjetaPersonaEnSituacionVulnerableCreator implements TarjetaCreator {
    private static final Logger logger = Logger.getLogger(TarjetaPersonaEnSituacionVulnerableCreator.class.getName());

    @Override
    public Tarjeta crearTarjeta(Object titular) {
        if (!(titular instanceof PersonaEnSituacionVulnerable)) {
            logger.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerableCreator.crearTarjeta_err"));
            throw new IllegalArgumentException(I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerableCreator.crearTarjeta_exception"));
        }

        return new TarjetaPersonaEnSituacionVulnerable((PersonaEnSituacionVulnerable) titular);
    }
}
