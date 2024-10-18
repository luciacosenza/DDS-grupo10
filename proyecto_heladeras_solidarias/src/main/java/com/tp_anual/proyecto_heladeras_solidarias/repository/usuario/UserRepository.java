package com.tp_anual.proyecto_heladeras_solidarias.repository.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
}
