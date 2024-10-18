package com.tp_anual.proyecto_heladeras_solidarias.service.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class UserCreator {
    public Usuario crearUser(String username, String password, Usuario.TipoUser tipo) {
        return new Usuario(username, password, tipo);
    }
}
