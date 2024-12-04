package com.tp_anual.proyecto_heladeras_solidarias.model.usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
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
