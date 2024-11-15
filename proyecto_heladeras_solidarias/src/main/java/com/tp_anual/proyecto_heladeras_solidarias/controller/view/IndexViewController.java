package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexViewController {

    public IndexViewController() {}

    @GetMapping("/")
    public String mostrarPaginaPrincipal(Model model) {
        setPaginaActual("/index", model);
        return "index";
    }

    void setPaginaActual(String pagina, Model model) {;
        model.addAttribute("paginaActual", pagina);
    }
}
