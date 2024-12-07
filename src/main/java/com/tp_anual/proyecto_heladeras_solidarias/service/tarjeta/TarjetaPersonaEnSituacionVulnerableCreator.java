package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta.DatosInvalidosCrearTarjetaPESVException;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.Tarjeta;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class TarjetaPersonaEnSituacionVulnerableCreator implements TarjetaCreator {

    private final GeneradorCodigo generadorCodigo;

    public TarjetaPersonaEnSituacionVulnerableCreator(GeneradorCodigo vGeneradorCodigo) {
        generadorCodigo = vGeneradorCodigo;
    }

    @Override
    public Tarjeta crearTarjeta(Object titular) throws DatosInvalidosCrearTarjetaPESVException {
        if (!(titular instanceof PersonaEnSituacionVulnerable)) {
            log.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerableCreator.crearTarjeta_err"));
            throw new DatosInvalidosCrearTarjetaPESVException();
        }

        String codigo = generadorCodigo.generarCodigo(false);
        return new TarjetaPersonaEnSituacionVulnerable(codigo, (PersonaEnSituacionVulnerable) titular);
    }
}
