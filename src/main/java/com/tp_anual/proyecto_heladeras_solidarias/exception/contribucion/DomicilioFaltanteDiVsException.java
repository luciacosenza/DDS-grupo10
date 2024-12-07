package com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DomicilioFaltanteDiVsException extends Exception {
    public DomicilioFaltanteDiVsException() {
        super(I18n.getMessage("contribucion.DistribucionViandas.validarIdentidad_exception"));
    }
}
