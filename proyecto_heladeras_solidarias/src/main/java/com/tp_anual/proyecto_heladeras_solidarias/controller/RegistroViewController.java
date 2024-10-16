package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroViewController {

    ColaboradorService colaboradorService;

    public RegistroViewController(ColaboradorService vColaboradorService) {
        colaboradorService = vColaboradorService;
    }
    
    @GetMapping("/registro-persona-humana")
    public String mostrarRegistroPersonaHumana(Model model) {
        model.addAttribute("colaboradorHumano", new ColaboradorHumano());
        return "registro-persona-humana";
    }

    @GetMapping("/registro-persona-juridica")
    public String mostrarRegistroPersonaJuridica() {
        return "registro-persona-juridica";
    }

    @GetMapping("/seleccion-persona")
    public String mostrarSeleccionPersona() {
        return "seleccion-persona";
    }

    @PostMapping("/registro-persona-humana/guardar")
    public String guardarPersonaHumana(@ModelAttribute("colaboradorHumano") ColaboradorHumano colaboradorHumano) {
        colaboradorService.guardarColaborador(colaboradorHumano);
        return "redirect:/crear-usuario";
    }
}
