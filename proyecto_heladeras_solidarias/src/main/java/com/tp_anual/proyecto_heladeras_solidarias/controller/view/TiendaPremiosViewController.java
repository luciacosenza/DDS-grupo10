package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TiendaPremiosViewController {

    private final OfertaService ofertaService;

    public TiendaPremiosViewController(OfertaService vOfertaService) {
        ofertaService = vOfertaService;
    }

    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        List<Oferta> ofertas = ofertaService.obtenerOfertas();
        model.addAttribute("ofertas", ofertas);

        return "tienda";
    }
}
