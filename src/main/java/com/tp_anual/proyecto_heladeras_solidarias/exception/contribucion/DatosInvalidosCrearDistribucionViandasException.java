package com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DatosInvalidosCrearDistribucionViandasException extends Exception {
    public DatosInvalidosCrearDistribucionViandasException() {
        super(I18n.getMessage("contribucion.DistribucionViandasCreator.crearContribucion_exception"));
    }
}
