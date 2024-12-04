package com.tp_anual.proyecto_heladeras_solidarias.repository.persona;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    
}
