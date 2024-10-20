package com.tp_anual.proyecto_heladeras_solidarias.exception;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class UsuarioRepetidoException extends Exception {
    public UsuarioRepetidoException() {
        super(I18n.getMessage("usuario.Usuario.validarUsuario.existeUsuario_exception"));
    }
}
