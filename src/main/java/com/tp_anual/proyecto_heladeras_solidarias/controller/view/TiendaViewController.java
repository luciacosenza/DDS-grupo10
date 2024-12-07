package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.exception.oferta.PuntosInsuficientesException;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TiendaViewController {

    private final OfertaService ofertaService;
    private final ColaboradorService colaboradorService;

    public TiendaViewController(OfertaService vOfertaService, ColaboradorService vColaboradorService) {
        ofertaService = vOfertaService;
        colaboradorService = vColaboradorService;
    }

    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        List<Oferta> ofertas = ofertaService.obtenerOfertas();
        model.addAttribute("ofertas", ofertas);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);

        model.addAttribute("colaborador", colaborador);

        return "tienda";
    }

    @GetMapping("/tienda/obtener-oferta/{id}")
    public String adquirirBeneficio(@PathVariable Long id) throws PuntosInsuficientesException {
        Oferta oferta = ofertaService.obtenerOferta(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        colaboradorService.intentarAdquirirBeneficio(colaborador.getId(), oferta);

        return "redirect:/tienda";
    }





}
