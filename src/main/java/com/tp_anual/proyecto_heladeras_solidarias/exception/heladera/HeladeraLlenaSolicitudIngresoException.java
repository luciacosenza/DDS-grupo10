package com.tp_anual.proyecto_heladeras_solidarias.exception.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class HeladeraLlenaSolicitudIngresoException extends Exception {
    public HeladeraLlenaSolicitudIngresoException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_llena", null, Locale.getDefault()));
    }
}
