package com.tp_anual.proyecto_heladeras_solidarias.exception.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class HeladeraVaciaSolicitudRetiroException extends Exception {
    public HeladeraVaciaSolicitudRetiroException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_vacia", null, Locale.getDefault()));
    }
}
