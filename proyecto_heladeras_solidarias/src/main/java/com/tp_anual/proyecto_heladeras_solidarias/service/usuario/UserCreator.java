package com.tp_anual.proyecto_heladeras_solidarias.service.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.User;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class UserCreator {
    public User crearUser(String username, String password, User.TipoUser tipo) {
        return new User(username, password, tipo);
    }
}
