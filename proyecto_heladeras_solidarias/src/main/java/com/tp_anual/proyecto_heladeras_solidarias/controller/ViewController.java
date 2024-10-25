package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;


@Controller
public class ViewController {

    public ViewController() {}

    @GetMapping("/colaborar-personas-fisicas")
    public String mostrarColaborarPersonasFisicas(Model model) {
        setPaginaActual("/colaborar-personas-fisicas", model);

        return "colaborar-personas-fisicas";
    }

    @GetMapping("/colaborar-personas-juridicas")
    public String mostrarColaborarPersonasJuridicas(Model model) {
        setPaginaActual("/colaborar", model);

        return "colaborar-personas-juridicas";
    }


    @GetMapping("/como-participar")
    public String mostrarComoParticipar(Model model) {
        setPaginaActual("/como-participar", model);

        return "como-participar";
    }


    @GetMapping("/mapa-heladeras")
    public String mostrarMapaHeladeras(Model model) {
        setPaginaActual("/mapa-heladeras", model);

        return "mapa-heladeras";
    }

    @GetMapping("/quienes-somos")
    public String mostrarQuienesSomos(Model model) {
        setPaginaActual("/quienes-somos", model);

        return "quienes-somos";
    }


    @GetMapping("/suscribirse")
    public String mostrarSuscribirse(Model model) {
        setPaginaActual("/suscribirse", model);

        return "suscribirse";
    }

    void setPaginaActual(String pagina, Model model) {;
        model.addAttribute("paginaActual", pagina);
    }
}
