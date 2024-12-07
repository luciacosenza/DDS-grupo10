package com.tp_anual.proyecto_heladeras_solidarias.exception.ubicador;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class TecnicosNoDisponiblesHeladeraException extends Exception {
    public TecnicosNoDisponiblesHeladeraException() {
        super(I18n.getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_exception"));
    }
}
