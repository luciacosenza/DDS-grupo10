package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.HeladeraRepository;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class TecnicoViewController {

    private final TecnicoService tecnicoService;
    private final HeladeraRepository heladeraRepository;
    private final AlertaService alertaService;
    private final FallaTecnicaService fallaTecnicaService;

    public TecnicoViewController(TecnicoService vTecnicoService, HeladeraRepository vHeladeraRepository, AlertaService vAlertaService, FallaTecnicaService vFallaTecnicaService) {
        tecnicoService = vTecnicoService;
        heladeraRepository = vHeladeraRepository;
        alertaService = vAlertaService;
        fallaTecnicaService = vFallaTecnicaService;
    }

    @GetMapping("/tecnico")
    public String mostrarPerfilTecnico(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Tecnico tecnico = tecnicoService.obtenerTecnicoPorUsername(username);

        model.addAttribute("alertas", alertaService.obtenerAlertasParaTecnico(tecnico));
        model.addAttribute("fallasTecnicas", fallaTecnicaService.obtenerFallasTecnicasParaTecnico(tecnico));

        List<Heladera> heladeras = heladeraRepository.findAll();
        model.addAttribute("heladeras", heladeras);

        return "tecnico";
    }

}
