package com.tp_anual.proyecto_heladeras_solidarias.exception.migrador;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class FilaDeDatosIncompletaException extends Exception {
    public FilaDeDatosIncompletaException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("migrador.TransformacionDeDatos.procesarColaborador_exception_fila_incompleta", null, Locale.getDefault()));
    }
}
