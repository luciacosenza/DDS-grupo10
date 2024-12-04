package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoFinder;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SuscripcionViewController {

    private final HeladeraService heladeraService;
    private final ColaboradorService colaboradorService;
    private final MedioDeContactoFinder medioDeContactoFinder;

    public SuscripcionViewController(HeladeraService vHeladeraService, ColaboradorService vColaboradorService, MedioDeContactoFinder vMedioDeContactoFinder) {
        heladeraService = vHeladeraService;
        colaboradorService = vColaboradorService;
        medioDeContactoFinder = vMedioDeContactoFinder;
    }

    @GetMapping("/suscribirse")
    public String mostrarFormSuscripcion(Model model) {
        setPaginaActual("/suscribirse", model);

        List<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        model.addAttribute("colaborador", colaborador);

        return "suscribirse";
    }

    @PostMapping("/suscribirse/guardar")
    public String guardarSuscripcion(
        @RequestParam("heladera") Long heladeraId,
        @RequestParam("condicion") CondicionSuscripcion condicion,
        @RequestParam("medio-de-contacto") Long medioDeContactoId,
        @RequestParam(value = "limite-inferior", defaultValue = "0") Integer limiteInferior,
        @RequestParam(value = "limite-superior", defaultValue = "0") Integer limiteSuperior)
    {
        Integer limite = Math.max(limiteInferior, limiteSuperior);

        Heladera heladera = heladeraService.obtenerHeladera(heladeraId);
        MedioDeContacto medioDeContacto = medioDeContactoFinder.obtenerMedioDeContacto(medioDeContactoId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumanoPorUsername(username);
        colaboradorService.suscribirse(colaborador.getId(), heladera, medioDeContacto, condicion, limite);

        return "redirect:/suscribirse";
    }

    void setPaginaActual(String pagina, Model model) {;
        model.addAttribute("paginaActual", pagina);
    }
}
