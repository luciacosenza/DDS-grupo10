package com.tp_anual.proyecto_heladeras_solidarias.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexViewController {

    public IndexViewController() {}

    @GetMapping("/")
    public String mostrarIndex(Model model, HttpSession session) {
        String userType = (String) session.getAttribute("userType");
        Boolean isLoggedIn = session.getAttribute("userType") != null;

        model.addAttribute("userType", userType);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("paginaActual", "/index");

        return "index";
    }
}
