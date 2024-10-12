package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService vOfertaService) {
        ofertaService = vOfertaService;
    }

    // TODO: Falta integrarlo en tienda.html
    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        ArrayList<Oferta> ofertas = ofertaService.obtenerOfertas();
        model.addAttribute("ofertas", ofertas);

        return "tienda";
    }

}
