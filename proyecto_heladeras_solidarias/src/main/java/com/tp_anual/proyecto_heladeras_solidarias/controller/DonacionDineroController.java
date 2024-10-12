package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionDineroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DonacionDineroController {

    private final DonacionDineroService donacionDineroService;

    public DonacionDineroController(DonacionDineroService vDonacionDineroService) {
        donacionDineroService = vDonacionDineroService;
    }
}
