package com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.repository.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class PersonaEnSituacionVulnerableService {

    private final PersonaEnSituacionVulnerableRepository personaEnSituacionVulnerableRepository;

    public PersonaEnSituacionVulnerableService(PersonaEnSituacionVulnerableRepository vPersonaEnSituacionVulnerableRepository) {
        personaEnSituacionVulnerableRepository = vPersonaEnSituacionVulnerableRepository;
    }

    public PersonaEnSituacionVulnerable obtenerPersonaEnSituacionVulnerable(Long personaEnSituacionVulnerableId) {
        return personaEnSituacionVulnerableRepository.findById(personaEnSituacionVulnerableId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public List<PersonaEnSituacionVulnerable> obtenerPersonasEnSituacionVulnerable() {
        return new ArrayList<>(personaEnSituacionVulnerableRepository.findAll());
    }

    public PersonaEnSituacionVulnerable guardarPersonaEnSituacionVulnerable(PersonaEnSituacionVulnerable personaEnSituacionVulnerable) {
        return personaEnSituacionVulnerableRepository.saveAndFlush(personaEnSituacionVulnerable);
    }

    public TarjetaPersonaEnSituacionVulnerable agregarTarjeta(Long personaEnSituacionVulnerableId, TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable) {
        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = obtenerPersonaEnSituacionVulnerable(personaEnSituacionVulnerableId);
        personaEnSituacionVulnerable.agregarTarjeta(tarjetaPersonaEnSituacionVulnerable);

        return guardarPersonaEnSituacionVulnerable(personaEnSituacionVulnerable).getTarjeta();
    }

    public Boolean poseeMenoresACargo(Long personaEnSituacionVulnerableId) {
        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = obtenerPersonaEnSituacionVulnerable(personaEnSituacionVulnerableId);
        return personaEnSituacionVulnerable.getMenoresACargo() > 0;
    }
}
