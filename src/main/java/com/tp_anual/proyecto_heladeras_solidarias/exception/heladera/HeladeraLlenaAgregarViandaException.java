package com.tp_anual.proyecto_heladeras_solidarias.exception.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class HeladeraLlenaAgregarViandaException extends Exception {
    public HeladeraLlenaAgregarViandaException() {
        super(I18n.getMessage("heladera.Heladera.agregarVianda_exception"));
    }
}
