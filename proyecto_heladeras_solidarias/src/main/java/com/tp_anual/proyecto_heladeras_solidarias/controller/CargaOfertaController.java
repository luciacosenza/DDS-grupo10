package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;



@Controller
public class CargaOfertaController {

    private final OfertaService ofertaService;

    public CargaOfertaController(OfertaService vOfertaService) {
        ofertaService = vOfertaService;
    }
}
