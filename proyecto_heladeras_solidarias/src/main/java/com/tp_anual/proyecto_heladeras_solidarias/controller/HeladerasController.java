package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HeladerasController {

    private final HeladeraService heladeraService;


    public HeladerasController(HeladeraService heladeraService) {
        this.heladeraService = heladeraService;
    }

    @GetMapping("/heladeras")
    public List<Heladera> obtenerHeladeras() {
        return heladeraService.obtenerHeladeras();
    }

}