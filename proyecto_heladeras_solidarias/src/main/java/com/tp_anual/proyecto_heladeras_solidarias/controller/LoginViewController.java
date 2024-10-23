package com.tp_anual.proyecto_heladeras_solidarias.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

    public LoginViewController() {}

    @GetMapping("/iniciar-sesion")
    public String login(Model model) {
        model.addAttribute("paginaActual", "/iniciar-sesion");

        return "/iniciar-sesion";
    }
}
