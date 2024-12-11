package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.FallaTecnica;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.HeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.AlertaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.FallaTecnicaService;
import org.springframework.ui.Model;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.service.tecnico.TecnicoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TecnicoViewController {

    private final TecnicoService tecnicoService;
    private final HeladeraRepository heladeraRepository;
    private final AlertaService alertaService;
    private final FallaTecnicaService fallaTecnicaService;

    private final I18nService i18nService;

    public TecnicoViewController(TecnicoService vTecnicoService, HeladeraRepository vHeladeraRepository, AlertaService vAlertaService, FallaTecnicaService vFallaTecnicaService, I18nService vI18nService) {
        tecnicoService = vTecnicoService;
        heladeraRepository = vHeladeraRepository;
        alertaService = vAlertaService;
        fallaTecnicaService = vFallaTecnicaService;
        i18nService = vI18nService;
    }

    @GetMapping("/tecnico")
    public String mostrarTecnico(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Tecnico tecnico = tecnicoService.obtenerTecnicoPorUsername(username);

        List<Alerta> alertas = new ArrayList<>();

        alertas.addAll(alertaService.obtenerAlertasParaTecnico(tecnico));
        alertas.addAll(alertaService.obtenerAlertasSinTecnico());

        model.addAttribute("alertas", alertas);

        List<FallaTecnica> fallasTecnicas = new ArrayList<>();

        fallasTecnicas.addAll(fallaTecnicaService.obtenerFallasTecnicasParaTecnico(tecnico));
        fallasTecnicas.addAll(fallaTecnicaService.obtenerFallasTecnicasSinTecnico());

        model.addAttribute("fallasTecnicas", fallasTecnicas);

        List<Heladera> heladeras = heladeraRepository.findAll();
        model.addAttribute("heladeras", heladeras);

        model.addAttribute("messageNoAlerts", i18nService.getMessage("controller.TecnicoController.mostrarTecnico_no_alertas"));
        model.addAttribute("messageNoFailures", i18nService.getMessage("controller.TecnicoController.mostrarTecnico_no_fallas_tecnicas"));

        return "tecnico";
    }

    @GetMapping("/tecnico/tomar-alerta{id}")
    public String tomarAlerta(@PathVariable Long id) {
        Alerta alerta = alertaService.obtenerAlerta(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Tecnico tecnico = tecnicoService.obtenerTecnicoPorUsername(username);
        Long tecnicoId = tecnico.getId();

        alertaService.asignarTecnico(id, tecnico);
        tecnicoService.agregarAPendientes(tecnicoId, alerta);

        return "redirect:/tecnico";
    }

    @GetMapping("/tecnico/tomar-falla-tecnica{id}")
    public String tomarFallaTecnica(@PathVariable Long id) {
        FallaTecnica fallaTecnica = fallaTecnicaService.obtenerFallaTecnica(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Tecnico tecnico = tecnicoService.obtenerTecnicoPorUsername(username);
        Long tecnicoId = tecnico.getId();

        fallaTecnicaService.asignarTecnico(id, tecnico);
        tecnicoService.agregarAPendientes(tecnicoId, fallaTecnica);

        return "redirect:/tecnico";
    }
}
