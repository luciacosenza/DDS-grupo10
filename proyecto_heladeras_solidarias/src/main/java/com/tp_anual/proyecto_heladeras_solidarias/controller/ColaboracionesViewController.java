package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DistribucionViandasService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ColaboracionesViewController {

    private final HeladeraService heladeraService;
    private final DistribucionViandasService distribucionViandasService;
    OfertaService ofertaService;

    public ColaboracionesViewController(OfertaService vOfertaService, HeladeraService heladeraService, DistribucionViandasService distribucionViandasService) {
        ofertaService = vOfertaService;
        this.heladeraService = heladeraService;
        this.distribucionViandasService = distribucionViandasService;
    }

    @GetMapping("/cargar-premio")
    public String mostrarCargarPremio(Model model) {
        model.addAttribute("oferta", new Oferta());
        setPaginaActual("/colaborar", model);
        return "cargar-premio";
    }

    @PostMapping("/cargar-premio/guardar")
    public String guardarPremio(@ModelAttribute Oferta oferta) {
        ofertaService.guardarOferta(oferta);

        return "redirect:/cargar-premio";
    }

    @GetMapping("/colocar-heladera")
    public String mostrarColocarHeladera(Model model) {
        setPaginaActual("/colaborar", model);
        return "colocar-heladera";
    }

    @PostMapping("/colocar-heladera/guardar")
    public String guardarHeladera(
            @RequestParam String nombre,
            @RequestParam("calle") String calle,
            @RequestParam("altura") String altura,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("cod-postal") String codigoPostal,
            @RequestParam("temp-minima") Float tempMinima,
            @RequestParam("temp-maxima") Float tempMaxima,
            @RequestParam("capacidad") Integer capacidad
             )
    {

        Ubicacion ubicacion = new Ubicacion(null, null, (calle + " " + altura), codigoPostal, ciudad, "Argentina");
        Heladera heladera = new Heladera(nombre, ubicacion, capacidad, tempMinima, tempMaxima, new ArrayList<>(), null, LocalDateTime.now(), false ); //TODO: la seteo en false para que despues se active ???
        heladera.setUbicacion(ubicacion);
        heladera.setFechaApertura(LocalDateTime.now());
        heladeraService.guardarHeladera(heladera);

        return "redirect:/colocar-heladera";
    }

    @GetMapping("/distribuir-viandas")
    public String mostrarDistribucionViandas(Model model) {
        List<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("paginaActual", "/colaborar");
        model.addAttribute("heladeras", heladeras);

        return "distribuir-viandas";
    }

    @PostMapping("/distribuir-viandas/guardar")
    public String guardarDistribucionVianda(
            @RequestParam("heladera-origen") Long heladeraOrigenId,
            @RequestParam("heladera-destino") Long heladeraDestinoId,
            @RequestParam("cantidad") Integer cantidadAMover,
            @RequestParam("motivo") DistribucionViandas.MotivoDistribucion motivo
    ) {
        Heladera heladeraOrigen = heladeraService.obtenerHeladera(heladeraOrigenId);
        Heladera heladeraDestino = heladeraService.obtenerHeladera(heladeraDestinoId);
        DistribucionViandas distribucionViandas = new DistribucionViandas(null, LocalDateTime.now(),heladeraOrigen, heladeraDestino, cantidadAMover, motivo);
        distribucionViandasService.guardarContribucion(distribucionViandas);

        //TODO : ver como guardamos los colaboradores

        return "redirect:/distribuir-viandas";
    }







    public void setPaginaActual(String pagina, Model model) {
        model.addAttribute("paginaActual", pagina);
    }
}
