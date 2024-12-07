package com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class DomicilioFaltanteRPESVException extends Exception {
    public DomicilioFaltanteRPESVException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.validarIdentidad_exception", null, Locale.getDefault()));
    }
}
