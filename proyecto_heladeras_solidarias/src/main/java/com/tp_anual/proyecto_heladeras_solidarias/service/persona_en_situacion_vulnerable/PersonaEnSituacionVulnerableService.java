package com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.repository.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class PersonaEnSituacionVulnerableService {

    private final PersonaEnSituacionVulnerableRepository personaEnSituacionVulnerableRepository;

    public PersonaEnSituacionVulnerableService(PersonaEnSituacionVulnerableRepository vPersonaEnSituacionVulnerableRepository) {
        personaEnSituacionVulnerableRepository = vPersonaEnSituacionVulnerableRepository;
    }

    public PersonaEnSituacionVulnerable obtenerPersonaEnSituacionVulnerable(Long personaEnSituacionVulnerableId) {
        return personaEnSituacionVulnerableRepository.findById(personaEnSituacionVulnerableId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public PersonaEnSituacionVulnerable guardarPersonaEnSituacionVulnerable(PersonaEnSituacionVulnerable personaEnSituacionVulnerable) {
        return personaEnSituacionVulnerableRepository.save(personaEnSituacionVulnerable);
    }
}
