package com.tp_anual.proyecto_heladeras_solidarias.exception.oferta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class PuntosInsuficientesException extends Exception {
    public PuntosInsuficientesException() {
        super(I18n.getMessage("oferta.Oferta.validarPuntos_exception"));
    }
}
