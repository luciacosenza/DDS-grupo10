package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HeladeraViewController {

    private final HeladeraService heladeraService;

    public HeladeraViewController(HeladeraService vHeladeraService) {
        heladeraService = vHeladeraService;
    }



   @GetMapping("/form-donar-vianda")
   public String mostrarFormDonacionVianda(Model model) {
        List<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);

        return "donar-vianda";
   }

   @GetMapping("/form-reportar-falla-tecnica")
   public String mostrarFormReportarFalla(Model model) {
        List<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);

        return "reportar-falla-tecnica";  //
    }

    // TODO: Implementar el @GetMapping para el admin


}
