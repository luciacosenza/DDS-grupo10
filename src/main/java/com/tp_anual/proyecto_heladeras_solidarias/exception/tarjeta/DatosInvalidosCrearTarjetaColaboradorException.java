package com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class DatosInvalidosCrearTarjetaColaboradorException extends Exception {
    public DatosInvalidosCrearTarjetaColaboradorException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("tarjeta.TarjetaColaboradorCreator.crearTarjeta_exception", null, Locale.getDefault()));
    }
}
