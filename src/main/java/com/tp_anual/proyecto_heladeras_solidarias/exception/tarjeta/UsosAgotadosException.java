package com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class UsosAgotadosException extends Exception {
    public UsosAgotadosException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerable.puedeUsar_exception", null, Locale.getDefault()));
    }
}
