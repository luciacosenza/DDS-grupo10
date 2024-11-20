package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexViewController {

    public IndexViewController() {}

    @GetMapping("/")
    public String mostrarPaginaPrincipal(
            @RequestParam(value = "loginSuccess", required = false) String loginSuccess,
            @RequestParam(value = "logout", required = false) String logout,

            Model model) {

        if (loginSuccess != null) {
            model.addAttribute("loginSuccess", true);
        }

        if (logout != null) {
            model.addAttribute("logoutSuccess", true);
        }

        setPaginaActual("/index", model);
        return "index";
    }

    void setPaginaActual(String pagina, Model model) {;
        model.addAttribute("paginaActual", pagina);
    }
}
