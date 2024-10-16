package com.tp_anual.proyecto_heladeras_solidarias.model.usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@Getter
@Log
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String username;

    @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    @Setter
    private TipoUser tipo;

    public enum TipoUser {
        COLABORADOR_HUMANO,
        COLABORADOR_JURIDICO,
        TECNICO,
        ADMIN
    }

    public User() {}

    public User(String vUsername, String vPassword, TipoUser vTipo) {
        username = username;
        password = password;
        tipo = vTipo;
    }
}
