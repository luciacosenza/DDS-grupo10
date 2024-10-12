package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DistribucionViandasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DistribucionViandasController {

    private final DistribucionViandasService distribucionViandasService;

    public DistribucionViandasController(DistribucionViandasService vDistribucionViandasService) {
        this.distribucionViandasService = vDistribucionViandasService;
    }

    @GetMapping("/traer-form-distribucion")
    public String mostrarFormulario(Model model) {
        model.addAttribute("distribucion-viandas", new DistribucionViandas());
        return "distribuir-viandas";
    }


}
