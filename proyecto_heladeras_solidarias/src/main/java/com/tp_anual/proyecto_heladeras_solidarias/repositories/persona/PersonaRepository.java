package com.tp_anual.proyecto_heladeras_solidarias.repositories.persona;

import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    
}
