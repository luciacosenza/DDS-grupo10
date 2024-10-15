package com.tp_anual.proyecto_heladeras_solidarias.repository.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
