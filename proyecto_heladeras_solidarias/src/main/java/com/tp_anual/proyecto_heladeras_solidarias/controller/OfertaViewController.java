package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OfertaViewController {

    private final OfertaService ofertaService;

    public OfertaViewController(OfertaService vOfertaService) {
        ofertaService = vOfertaService;
    }

    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        List<Oferta> ofertas = ofertaService.obtenerOfertas();
        model.addAttribute("ofertas", ofertas);

        return "tienda";
    }

//    @PostMapping("/guardar")
//    public String guardarTienda(@ModelAttribute Oferta oferta) {
//        ofertaService.guardarOferta(oferta);
//        return "redirect:/cargar-premio";
//    }

}
