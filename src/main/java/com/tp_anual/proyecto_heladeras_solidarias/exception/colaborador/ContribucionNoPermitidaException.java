package com.tp_anual.proyecto_heladeras_solidarias.exception.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class ContribucionNoPermitidaException extends Exception {
    public ContribucionNoPermitidaException() {
        super(I18n.getMessage("colaborador.Colaborador.colaborar_exception"));
    }
}
