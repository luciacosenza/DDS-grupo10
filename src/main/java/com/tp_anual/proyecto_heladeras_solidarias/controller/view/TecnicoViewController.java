package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.HeladeraRepository;
import org.springframework.ui.Model;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.IncidenteService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tecnico.TecnicoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TecnicoViewController {

    private final TecnicoService tecnicoService;
    private final HeladeraRepository heladeraRepository;
    IncidenteService incidenteService;

    public TecnicoViewController(IncidenteService vIncidenteService, TecnicoService vTecnicoService, HeladeraRepository vHeladeraRepository) {
        this.incidenteService = vIncidenteService;
        this.tecnicoService = vTecnicoService;
        this.heladeraRepository = vHeladeraRepository;
    }

    @GetMapping("/tecnico")
    public String mostrarPerfilTecnico(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Tecnico tecnico = tecnicoService.obtenerTecnicoPorUsername(username);
        List<Incidente> incidentes =  incidenteService.obtenerIncidentesParaTecnico(tecnico);

        model.addAttribute("incidentes", incidentes);

        List<Heladera> heladeras = heladeraRepository.findAll();

        model.addAttribute("heladeras", heladeras);


        return "tecnico";
    }

}
