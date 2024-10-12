package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DistribucionViandasService;
import com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrarPersonaEnSituacionVulnerableController {
    private final PersonaEnSituacionVulnerableService personaEnSituacionVulnerableService;

    public RegistrarPersonaEnSituacionVulnerableController(PersonaEnSituacionVulnerableService vPersonaEnSituacionVulnerableService){
        this.personaEnSituacionVulnerableService = vPersonaEnSituacionVulnerableService;
    }
}
