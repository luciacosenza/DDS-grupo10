package com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DatosInvalidosCrearDonacionViandaException extends Exception {
    public DatosInvalidosCrearDonacionViandaException() {
        super(I18n.getMessage("contribucion.DonacionViandaCreator.crearContribucion_exception"));
    }
}
