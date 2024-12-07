package com.tp_anual.proyecto_heladeras_solidarias.exception.migrador;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class FilaDeDatosIncompletaException extends Exception {
    public FilaDeDatosIncompletaException() {
        super(I18n.getMessage("migrador.TransformacionDeDatos.procesarColaborador_exception_fila_incompleta"));
    }
}
