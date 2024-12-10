package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Map;


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

    void setPaginaActual(String pagina, Model model) {;
        model.addAttribute("paginaActual", pagina);
    }
}
