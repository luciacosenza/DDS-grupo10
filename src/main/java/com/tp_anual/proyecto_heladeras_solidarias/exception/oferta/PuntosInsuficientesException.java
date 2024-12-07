package com.tp_anual.proyecto_heladeras_solidarias.exception.oferta;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class PuntosInsuficientesException extends Exception {
    public PuntosInsuficientesException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("oferta.Oferta.validarPuntos_exception", null, Locale.getDefault()));
    }
}
