package com.tp_anual.proyecto_heladeras_solidarias.repository.tecnico;

import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    Tecnico findByUsuario_Username(String username);

}