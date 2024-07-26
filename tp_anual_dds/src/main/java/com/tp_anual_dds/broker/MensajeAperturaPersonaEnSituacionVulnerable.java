package com.tp_anual_dds.broker;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;

public class MensajeAperturaPersonaEnSituacionVulnerable implements Mensaje {
    private final HeladeraActiva heladera;
    private final PersonaEnSituacionVulnerable personaEnSituacionVulnerable;

    public MensajeAperturaPersonaEnSituacionVulnerable(HeladeraActiva vHeladera, PersonaEnSituacionVulnerable vPersonaEnSituacionVulnerable) {
        heladera = vHeladera;
        personaEnSituacionVulnerable = vPersonaEnSituacionVulnerable;
    }

    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public PersonaEnSituacionVulnerable getPersonaEnSituacionVulnerable() {
        return personaEnSituacionVulnerable;
    }

    @Override
    public void procesar() {
        heladera.getGestorDeAperturas().revisarPermisoAperturaP(personaEnSituacionVulnerable);
    }
}
