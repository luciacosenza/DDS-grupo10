package com.tp_anual.proyecto_heladeras_solidarias.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

public class IndexController {
    
    @GetMapping("/")
    public String mostrarIndex(Model model, HttpSession session) {
        // Supongamos que tienes almacenado el tipo de usuario en la sesión
        String userType = (String) session.getAttribute("userType"); // Ejemplo: "colaborador_juridico" o "colaborador_humano"
        boolean isLoggedIn = session.getAttribute("userType") != null; // Si hay usuario en sesión

        // Pasamos los atributos a la vista
        model.addAttribute("userType", userType);
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "index"; // Devuelve el nombre de la vista
    }
}
