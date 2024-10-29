package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaPersonaEnSituacionVulnerableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ContribucionViewController {

    private final HeladeraService heladeraService;
    private final OfertaService ofertaService;
    private final TarjetaPersonaEnSituacionVulnerableService tarjetaPersonaEnSituacionVulnerableService;
    private final CargaOfertaService cargaOfertaService;
    private final DistribucionViandasService distribucionViandasService;
    private final DonacionDineroService donacionDineroService;
    private final DonacionViandaService donacionViandaService;
    private final HacerseCargoDeHeladeraService hacerseCargoDeHeladeraService;
    private final RegistroDePersonaEnSituacionVulnerableService registroDePersonaEnSituacionVulnerableService;
    private final PersonaEnSituacionVulnerableService personaEnSituacionVulnerableService;

    public ContribucionViewController(OfertaService vOfertaService, HeladeraService vHeladeraService, PersonaEnSituacionVulnerableService vPersonaEnSituacionVulnerableService, TarjetaPersonaEnSituacionVulnerableService vTarjetaPersonaEnSituacionVulnerableService, CargaOfertaService vCargaOfertaService, DistribucionViandasService vDistribucionViandasService, DonacionDineroService vDonacionDineroService, DonacionViandaService vDonacionViandaService, HacerseCargoDeHeladeraService vHacerseCargoDeHeladeraService, RegistroDePersonaEnSituacionVulnerableService vRegistroDePersonaEnSituacionVulnerableService) {
        ofertaService = vOfertaService;
        heladeraService = vHeladeraService;
        personaEnSituacionVulnerableService = vPersonaEnSituacionVulnerableService;
        tarjetaPersonaEnSituacionVulnerableService = vTarjetaPersonaEnSituacionVulnerableService;
        cargaOfertaService = vCargaOfertaService;
        distribucionViandasService = vDistribucionViandasService;
        donacionDineroService = vDonacionDineroService;
        donacionViandaService = vDonacionViandaService;
        hacerseCargoDeHeladeraService = vHacerseCargoDeHeladeraService;
        registroDePersonaEnSituacionVulnerableService = vRegistroDePersonaEnSituacionVulnerableService;
    }

    @GetMapping("/cargar-premio")
    public String mostrarFormCargarPremio(Model model) {
        setPaginaActual("/colaborar", model);
        model.addAttribute("oferta", new Oferta());

        return "cargar-premio";
    }

    @PostMapping("/cargar-premio/guardar")
    public String guardarCargaOferta(@ModelAttribute Oferta oferta) {
        ofertaService.guardarOferta(oferta);

        CargaOferta cargaOferta = new CargaOferta(null, null, oferta);
        cargaOfertaService.guardarContribucion(cargaOferta);    // TODO: Hay que cambiar esto (la contribución se carga con el método colaborar del colaborador)

        return "redirect:/cargar-premio";
    }

    @GetMapping("/distribuir-viandas")
    public String mostrarFormDistribuirViandas(Model model) {
        setPaginaActual("/colaborar", model);
        List<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);

        return "distribuir-viandas";
    }

    @PostMapping("/distribuir-viandas/guardar")
    public String guardarDistribucionVianda(
        @RequestParam("heladera-origen") Long heladeraOrigenId,
        @RequestParam("heladera-destino") Long heladeraDestinoId,
        @RequestParam("cantidad") Integer cantidadAMover,
        @RequestParam("motivo") DistribucionViandas.MotivoDistribucion motivo)
    {
        Heladera heladeraOrigen = heladeraService.obtenerHeladera(heladeraOrigenId);
        Heladera heladeraDestino = heladeraService.obtenerHeladera(heladeraDestinoId);

        DistribucionViandas distribucionViandas = new DistribucionViandas(null, null, heladeraOrigen, heladeraDestino, cantidadAMover, motivo);
        distribucionViandasService.guardarContribucion(distribucionViandas);    // TODO: Hay que cambiar esto (la contribución se carga con el método colaborar del colaborador)

        return "redirect:/distribuir-viandas";
    }

    @GetMapping("/donar-dinero")
    public String mostrarFormDonarDinero(Model model) {
        setPaginaActual("/colaborar", model);

        return "donar-dinero";
    }

    @PostMapping("/donar-dinero/guardar")
    public String guardarDonacionDinero(
        @RequestParam("monto") Double monto,
        @RequestParam("frecuencia") DonacionDinero.FrecuenciaDePago frecuencia)
    {
        DonacionDinero donacionDinero = new DonacionDinero(null, null, monto, frecuencia);
        donacionDineroService.guardarContribucion(donacionDinero);  // TODO: Hay que cambiar esto (la contribución se carga con el método colaborar del colaborador)

        return "redirect:/donar-dinero";
    }

    @GetMapping("/donar-vianda")
    public String mostrarFormDonarVianda(Model model) {
        List<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);
        setPaginaActual("/colaborar", model);

        return "donar-vianda";
    }

    @PostMapping("/donar-vianda/guardar")
    public String guardarDonacionVianda(
        @RequestParam("comida") String comida,
        @RequestParam("heladera") Long heladeraId,
        @RequestParam("fecha-caducidad") LocalDate fechaCaducidad,
        @RequestParam("calorias") Integer calorias,
        @RequestParam("peso") Integer peso)
    {
        Heladera heladera = heladeraService.obtenerHeladera(heladeraId);
        Vianda vianda = new Vianda(comida, null, fechaCaducidad, LocalDateTime.now(), calorias, peso, false);
        DonacionVianda donacionVianda = new DonacionVianda(null, null, vianda, heladera);
        donacionViandaService.guardarContribucion(donacionVianda);  // TODO: Hay que cambiar esto (la contribución se carga con el método colaborar del colaborador)

        return "redirect:/donar-vianda";
    }

    @GetMapping("/colocar-heladera")
    public String mostrarFormColocarHeladera(Model model) {
        setPaginaActual("/colaborar", model);
        model.addAttribute("heladera", new Heladera());

        return "colocar-heladera";
    }

    @PostMapping("/colocar-heladera/guardar")
    public String guardarHeladera(
            @RequestParam String nombre,
            @RequestParam("calle") String calle,
            @RequestParam("altura") String altura,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("codigo-postal") String codigoPostal,
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

    @GetMapping("/registrar-persona-vulnerable")
    public String mostrarRegistrarPersonaVulnerable(Model model) {
        setPaginaActual("/colaborar", model);

        return "registrar-persona-vulnerable";
    }

    @PostMapping("/registrar-persona-vulnerable/guardar")
    public String guardarRegistroDePersonaEnSituacionvulnerable(
        @RequestParam("nombre") String nombre,
        @RequestParam("apellido") String apellido,
        @RequestParam("fecha-nacimiento") LocalDate fechaNacimiento,
        @RequestParam("tipo-documento") Documento.TipoDocumento tipoDocumento,
        @RequestParam("numero-documento") String numeroDocumento,
        @RequestParam("sexo-documento") Documento.Sexo sexoDocumento,
        @RequestParam(value = "posee-domicilio", defaultValue = "false") Boolean poseeDomicilio,
        @RequestParam("calle") String calle,
        @RequestParam("altura") String altura,
        @RequestParam("ciudad") String ciudad,
        @RequestParam("codigo-postal") String codigoPostal,
        @RequestParam(value = "menores-a-cargo", defaultValue = "0") Integer menoresACargo)
    {

        Documento documento = new Documento(tipoDocumento, numeroDocumento, sexoDocumento);
        PersonaFisica personaFisica = new PersonaFisica(nombre, apellido, documento, fechaNacimiento);

        Ubicacion domicilio = poseeDomicilio ? new Ubicacion(null, null, (calle + " " + altura), codigoPostal, ciudad, "Argentina") : null; // TODO: Hay que arreglar esto (cuando seleccionamos y deseleccionamos el checkbox de posee domicilio, se buguea)

        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = new PersonaEnSituacionVulnerable(personaFisica, domicilio, null, menoresACargo, poseeDomicilio);
        Long personaEnSituacionVulnerableId = personaEnSituacionVulnerableService.guardarPersonaEnSituacionVulnerable(personaEnSituacionVulnerable).getId();

        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = tarjetaPersonaEnSituacionVulnerableService.crearTarjeta(personaEnSituacionVulnerableId);

        RegistroDePersonaEnSituacionVulnerable registroDePersonaEnSituacionVulnerable = new RegistroDePersonaEnSituacionVulnerable(null, null, tarjetaPersonaEnSituacionVulnerable);
        registroDePersonaEnSituacionVulnerableService.guardarContribucion(registroDePersonaEnSituacionVulnerable);

        return "redirect:/registrar-persona-vulnerable";
    }

    public void setPaginaActual(String pagina, Model model) {
        model.addAttribute("paginaActual", pagina);
    }
}
