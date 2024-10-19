package com.tp_anual.proyecto_heladeras_solidarias.service.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class UsuarioCreator {
    public Usuario crearUsuario(String username, String password, Usuario.TipoUsuario tipo) {
        return new Usuario(username, password, tipo);
    }
}
