package com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DatosInvalidosCrearRPESVException extends Exception {
    public DatosInvalidosCrearRPESVException() {
        super(I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerableCreator.crearContribucion_exception"));
    }
}
