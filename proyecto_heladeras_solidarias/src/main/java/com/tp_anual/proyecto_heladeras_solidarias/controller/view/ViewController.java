package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewController {

    public ViewController() {}

    @GetMapping("/quienes-somos")
    public String mostrarQuienesSomos(Model model) {
        setPaginaActual("/quienes-somos", model);

        return "quienes-somos";
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

    @GetMapping("/suscribirse")
    public String mostrarSuscribirse(Model model) {
        setPaginaActual("/suscribirse", model);

        return "suscribirse";
    }

    void setPaginaActual(String pagina, Model model) {;
        model.addAttribute("paginaActual", pagina);
    }
}
