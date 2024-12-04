package com.tp_anual.proyecto_heladeras_solidarias.repository.persona;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaJuridicaRepository extends JpaRepository<PersonaJuridica, Long> {
    
}
