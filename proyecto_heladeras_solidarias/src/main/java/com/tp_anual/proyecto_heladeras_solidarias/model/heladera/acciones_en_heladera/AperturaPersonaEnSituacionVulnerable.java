package com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@DiscriminatorValue("Acción Persona en Situación Vulnerable")
@Getter
public class AperturaPersonaEnSituacionVulnerable extends AccionHeladera {
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_en_situacion_vulnerable_id")
    private final PersonaEnSituacionVulnerable responsable;

    public AperturaPersonaEnSituacionVulnerable(LocalDateTime vFecha, Heladera vHeladera, PersonaEnSituacionVulnerable vResponsable) {
        super(vFecha, vHeladera);
        responsable = vResponsable;
    }
}
