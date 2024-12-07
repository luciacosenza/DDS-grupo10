package com.tp_anual.proyecto_heladeras_solidarias.exception.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class PermisoAperturaExpiradoException extends Exception {
    public PermisoAperturaExpiradoException() {
        super(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_exception"));
    }
}
