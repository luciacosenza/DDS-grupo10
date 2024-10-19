package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CargarPremioViewController {

    OfertaService ofertaService;

    public CargarPremioViewController(OfertaService vOfertaService) {
        ofertaService = vOfertaService;
    }

    @GetMapping("/cargar-premio")
    public String mostrarCargarPremio(Model model) {
        model.addAttribute("oferta", new Oferta());

        return "cargar-premio";
    }

    @PostMapping("/cargar-premio/guardar")
    public String guardarPremio(@ModelAttribute Oferta oferta) {
        ofertaService.guardarOferta(oferta);

        return "redirect:/cargar-premio";
    }
}
