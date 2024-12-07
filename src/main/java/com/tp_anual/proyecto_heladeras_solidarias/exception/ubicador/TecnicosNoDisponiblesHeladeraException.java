package com.tp_anual.proyecto_heladeras_solidarias.exception.ubicador;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class TecnicosNoDisponiblesHeladeraException extends Exception {
    public TecnicosNoDisponiblesHeladeraException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_exception", null, Locale.getDefault()));
    }
}
