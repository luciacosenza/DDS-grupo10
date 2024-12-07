package com.tp_anual.proyecto_heladeras_solidarias.exception.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class SuscripcionNoCorrespondeAColaboradorException extends Exception {
    public SuscripcionNoCorrespondeAColaboradorException() {
        super(I18n.getMessage("colaborador.ColaboradorHumano.modificarSuscripcion_exception"));
    }
}
