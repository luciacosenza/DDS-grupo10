package com.tp_anual.proyecto_heladeras_solidarias.exception.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class HeladeraVaciaRetirarViandaException extends Exception {
    public HeladeraVaciaRetirarViandaException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("heladera.Heladera.retirarVianda_exception", null, Locale.getDefault()));
    }
}
