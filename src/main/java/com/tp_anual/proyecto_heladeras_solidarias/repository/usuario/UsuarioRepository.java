package com.tp_anual.proyecto_heladeras_solidarias.repository.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query(nativeQuery = true, name = "Usuario.findUsuarioParaColaborador")
    Optional<Usuario> findUsuarioParaColaborador(@Param("colaborador") Long colaboradorId);
}
