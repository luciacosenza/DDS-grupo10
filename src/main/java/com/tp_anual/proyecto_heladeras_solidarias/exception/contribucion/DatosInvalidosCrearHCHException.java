package com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DatosInvalidosCrearHCHException extends Exception {
    public DatosInvalidosCrearHCHException() {
        super(I18n.getMessage("contribucion.HacerseCargoDeHeladeraCreator.crearContribucion_exception"));
    }
}
