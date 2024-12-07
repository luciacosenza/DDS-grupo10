package com.tp_anual.proyecto_heladeras_solidarias.exception.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class PermisoAperturaExpiradoException extends Exception {
    public PermisoAperturaExpiradoException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_exception", null, Locale.getDefault()));
    }
}
