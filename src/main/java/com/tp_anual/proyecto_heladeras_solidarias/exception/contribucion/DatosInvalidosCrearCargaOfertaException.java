package com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DatosInvalidosCrearCargaOfertaException extends Exception {
    public DatosInvalidosCrearCargaOfertaException() {
        super(I18n.getMessage("contribucion.CargaOfertaCreator.crearContribucion_exception"));
    }
}
