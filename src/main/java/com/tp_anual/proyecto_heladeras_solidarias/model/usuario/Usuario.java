package com.tp_anual.proyecto_heladeras_solidarias.model.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@NamedNativeQuery(
        name = "Usuario.findUsuarioParaColaborador",
        query = "SELECT * FROM usuario AS u " +
                "INNER JOIN colaborador AS c " +
                "ON u.id = c.usuario " +
                "WHERE u.id = colaborador.usuario AND colaborador.id = :colaborador",
        resultClass = Usuario.class
)
@Getter
@Log
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String username;

    @Setter
    private String password;

    @Setter
    private String role;

    public Usuario() {}

    public Usuario(String vUsername, String vPassword, String vRole) {
        username = vUsername;
        password = vPassword;
        role = vRole;
    }
}
