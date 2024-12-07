package com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DatosInvalidosCrearDonacionDineroException extends Exception {
    public DatosInvalidosCrearDonacionDineroException() {
        super(I18n.getMessage("contribucion.DonacionDineroCreator.crearContribucion_exception"));
    }
}
