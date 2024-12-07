package com.tp_anual.proyecto_heladeras_solidarias.exception.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class HeladeraVaciaRetirarViandaException extends Exception {
    public HeladeraVaciaRetirarViandaException() {
        super(I18n.getMessage("heladera.Heladera.retirarVianda_exception"));
    }
}
