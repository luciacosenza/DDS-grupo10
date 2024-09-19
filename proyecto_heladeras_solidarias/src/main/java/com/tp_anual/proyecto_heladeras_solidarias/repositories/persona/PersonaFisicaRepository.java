package com.tp_anual.proyecto_heladeras_solidarias.repositories.persona;

import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.PersonaFisica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaFisicaRepository extends JpaRepository<PersonaFisica, Long> {
    
}
