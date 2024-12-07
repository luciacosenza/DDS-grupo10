package com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class UsosAgotadosException extends Exception {
    public UsosAgotadosException() {
        super(I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerable.puedeUsar_exception"));
    }
}
