package com.tp_anual.proyecto_heladeras_solidarias.exception.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class HeladeraVaciaSolicitudRetiroException extends Exception {
    public HeladeraVaciaSolicitudRetiroException() {
        super(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_vacia"));
    }
}
