package com.tp_anual.proyecto_heladeras_solidarias.exception.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class HeladeraVaciaIntentoRetiroPESVException extends Exception {
    public HeladeraVaciaIntentoRetiroPESVException() {
        super(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_heladera_vacia"));
    }
}
