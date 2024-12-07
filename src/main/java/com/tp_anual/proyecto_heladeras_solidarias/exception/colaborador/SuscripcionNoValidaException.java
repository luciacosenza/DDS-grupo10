package com.tp_anual.proyecto_heladeras_solidarias.exception.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class SuscripcionNoValidaException extends Exception {
    public SuscripcionNoValidaException() {
        super(I18n.getMessage("colaborador.ColaboradorHumano.suscribirse_exception"));
    }
}
