package com.tp_anual.proyecto_heladeras_solidarias.repositories.tecnico;

import com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    
}