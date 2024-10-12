package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.service.reporte.ReporteService;
import org.springframework.stereotype.Controller;

@Controller
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService vReporteService) {
        reporteService = vReporteService;
    }

    // TODO: Implementar el @GetMapping (no podemos hacerlo porque cada Reporte es una vista y todavía no sabemos cómo hacerlo)
}
