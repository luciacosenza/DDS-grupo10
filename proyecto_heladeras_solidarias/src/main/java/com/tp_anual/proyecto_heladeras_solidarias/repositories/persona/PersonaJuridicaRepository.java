package com.tp_anual.proyecto_heladeras_solidarias.repositories.persona;

import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.PersonaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaJuridicaRepository extends JpaRepository<PersonaJuridica, Long> {
    
}
