package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
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
public class FallaTecnicaViewController {

    private final HeladeraService heladeraService;
    private final ColaboradorService colaboradorService;

    public FallaTecnicaViewController(HeladeraService vHeladeraService, ColaboradorService vColaboradorService) {
        heladeraService = vHeladeraService;
        colaboradorService = vColaboradorService;
    }

    @GetMapping("/reportar-falla-tecnica")
    public String mostrarFormReportarFallaTecnica(Model model) {
        setPaginaActual("/reportar-falla-tecnica", model);
        List<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);

        return "reportar-falla-tecnica";
    }

    @PostMapping("/reportar-falla-tecnica/guardar")
    public String guardarFallaTecnica(
        @RequestParam("heladera") Long heladeraId,
        @RequestParam("descripcion") String descripcion,
        @RequestParam("foto") String foto)
    {
        Heladera heladera = heladeraService.obtenerHeladera(heladeraId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        colaboradorService.reportarFallaTecnica(colaborador.getId(), heladera, descripcion, foto);

        return "redirect:/reportar-falla-tecnica";
    }

    // TODO: Implementar el @GetMapping para el admin

    void setPaginaActual(String pagina, Model model) {;
        model.addAttribute("paginaActual", pagina);
    }
}
