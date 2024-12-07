package com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DatosInvalidosCrearTarjetaColaboradorException extends Exception {
    public DatosInvalidosCrearTarjetaColaboradorException() {
        super(I18n.getMessage("tarjeta.TarjetaColaboradorCreator.crearTarjeta_exception"));
    }
}
