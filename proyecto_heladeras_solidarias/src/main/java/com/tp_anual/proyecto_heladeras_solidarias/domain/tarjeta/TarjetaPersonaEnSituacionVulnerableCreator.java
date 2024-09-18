package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class TarjetaPersonaEnSituacionVulnerableCreator implements TarjetaCreator {

    @Override
    public Tarjeta crearTarjeta(Object titular) {
        if (!(titular instanceof PersonaEnSituacionVulnerable)) {
            log.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerableCreator.crearTarjeta_err"));
            throw new IllegalArgumentException(I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerableCreator.crearTarjeta_exception"));
        }

        return new TarjetaPersonaEnSituacionVulnerable((PersonaEnSituacionVulnerable) titular);
    }
}
