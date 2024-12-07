package com.tp_anual.proyecto_heladeras_solidarias.exception.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class PasswordNoValidaException extends Exception {
    public PasswordNoValidaException() {
        super(SpringContext.getBean(MessageSource.class).getMessage("usuario.Usuario.validarUsuario.esPasswordValida_exception", null, Locale.getDefault()));
    }
}
