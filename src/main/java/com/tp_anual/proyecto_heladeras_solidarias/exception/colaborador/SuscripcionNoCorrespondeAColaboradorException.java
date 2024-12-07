package com.tp_anual.proyecto_heladeras_solidarias.exception.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class SuscripcionNoCorrespondeAColaboradorException extends Exception {
    public SuscripcionNoCorrespondeAColaboradorException() {

        super(SpringContext.getBean(MessageSource.class).getMessage("colaborador.ColaboradorHumano.modificarSuscripcion_exception", null, Locale.getDefault()));
    }
}
