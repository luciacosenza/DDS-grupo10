package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SuscribirseViewController {

    private final HeladeraService heladeraService;

    public SuscribirseViewController(HeladeraService vHeladeraService) {
        heladeraService = vHeladeraService;
    }

    @GetMapping("/suscribirse")
    public String mostrarFormSuscripcion(Model model) {
        setPaginaActual("/suscribirse", model);
        List<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);

        return "suscribirse";
    }

    /* TODO: @PostMapping("/suscribirse/guardar")
    public String guardarSuscripcion(Model model, Heladera heladera) {}*/

    void setPaginaActual(String pagina, Model model) {;
        model.addAttribute("paginaActual", pagina);
    }
}
