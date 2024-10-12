package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;

//import org.hibernate.mapping.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

public class HeladerasController {

    private final HeladeraService heladeraService;

    public HeladerasController(HeladeraService vHeladeraService) {
        heladeraService = vHeladeraService;
    }

    @GetMapping("/form-distribuir-viandas")
    public String mostrarFormDistribucionViandas(Model model) {
        ArrayList<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);
        return "distribuir-viandas";
    }

   @GetMapping("/form-donar-vianda")
   public String mostrarFormDonacionVianda(Model model) {
        ArrayList<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);
        return "donar-vianda";
   }

   @GetMapping("/form-reportar-falla-tecnica")
    public String mostrarFormReportarFalla(Model model) {
        ArrayList<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);
        return "reportar-falla-tecnica";  //
    }
}
