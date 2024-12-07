package com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DomicilioFaltanteDoVException extends Exception {
    public DomicilioFaltanteDoVException() {
        super(I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_exception"));
    }
}
