package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.Persona;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @PostMapping("/registro-persona-humana/guardar")
    public String guardarPersonaHumana(@ModelAttribute("persona") Persona persona) {

    }
}
