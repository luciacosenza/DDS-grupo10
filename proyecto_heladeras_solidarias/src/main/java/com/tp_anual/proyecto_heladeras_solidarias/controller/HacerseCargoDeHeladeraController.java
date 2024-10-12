package com.tp_anual.proyecto_heladeras_solidarias.controller;


import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HacerseCargoDeHeladeraController {

    private final HeladeraService heladeraService;

    public HacerseCargoDeHeladeraController(HeladeraService vHeladeraService) {
        heladeraService = vHeladeraService;
    }

}
