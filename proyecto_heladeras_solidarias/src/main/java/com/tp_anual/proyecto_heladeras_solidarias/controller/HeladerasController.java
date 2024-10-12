package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;

//import org.hibernate.mapping.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

public class HeladerasController {

    private final HeladeraService heladeraService;

    public HeladerasController(HeladeraService vHeladeraService) {
        heladeraService = vHeladeraService;
    }

    @GetMapping("/heladeras")
    public String mostrarListaHeladeras(Model model) {
        List<Heladera> heladeras = heladeraService.obtenerTodasLasHeladeras();
        model.addAttribute("heladeras", heladeras);
        return "listado_heladeras";  // Vista que muestra la lista de heladeras
    }
    
}
