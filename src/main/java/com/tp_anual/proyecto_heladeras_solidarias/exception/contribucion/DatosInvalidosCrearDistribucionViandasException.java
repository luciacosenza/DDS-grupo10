package com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class DatosInvalidosCrearDistribucionViandasException extends Exception {
    public DatosInvalidosCrearDistribucionViandasException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("contribucion.DistribucionViandasCreator.crearContribucion_exception", null, Locale.getDefault()));
    }
}
