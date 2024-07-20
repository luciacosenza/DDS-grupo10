package com.tp_anual_dds.domain.heladera.acciones_en_heladera;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;

public class AperturaPersonaEnSituacionVulnerable extends AccionHeladera {
    public AperturaPersonaEnSituacionVulnerable(LocalDateTime vFecha, HeladeraActiva vHeladera, PersonaEnSituacionVulnerable vResponsable) {
        fecha = vFecha;
        heladera = vHeladera;
        responsable = vResponsable;
    }
}
