package com.tp_anual.proyecto_heladeras_solidarias.exception;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class PasswordNoValidaException extends Exception {
    public PasswordNoValidaException() {
        super(I18n.getMessage("usuario.Usuario.validarUsuario.esPasswordValida_exception"));
    }
}
