package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

public class HeladerasController {

    private final HeladeraService heladeraService;

    public HeladerasController(HeladeraService vHeladeraService) {
        heladeraService = vHeladeraService;
    }

    @GetMapping("/reportar-falla")
    public String mostrarFormularioFalla(Model model) {
        // Obtener la lista de nombres de las heladeras desde el servicio
        ArrayList<String> heladeras = heladeraService.obtenerNombresDeHeladeras();

        // Pasar los nombres de heladeras al modelo
        model.addAttribute("heladeras", heladeras);
        return "reportar_falla_tecnica";
    }
}
