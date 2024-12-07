package com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class DatosInvalidosCrearTarjetaPESVException extends Exception {
    public DatosInvalidosCrearTarjetaPESVException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("tarjeta.TarjetaPersonaEnSituacionVulnerableCreator.crearTarjeta_exception", null, Locale.getDefault()));
    }
}
