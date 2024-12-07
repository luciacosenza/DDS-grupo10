package com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DatosInvalidosCrearTarjetaPESVException extends Exception {
    public DatosInvalidosCrearTarjetaPESVException() {
        super(I18n.getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerableCreator.crearTarjeta_exception"));
    }
}
