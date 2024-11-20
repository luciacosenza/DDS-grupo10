package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaPersonaEnSituacionVulnerableService;
import com.tp_anual.proyecto_heladeras_solidarias.service.usuario.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final PersonaEnSituacionVulnerableService personaEnSituacionVulnerableService;
    private final ColaboradorService colaboradorService;
    private final CargaOfertaCreator cargaOfertaCreator;
    private final DistribucionViandasCreator distribucionViandasCreator;
    private final DonacionDineroCreator donacionDineroCreator;
    private final DonacionViandaCreator donacionViandaCreator;
    private final HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator;
    private final RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator;

    public ContribucionViewController(OfertaService vOfertaService, HeladeraService vHeladeraService, PersonaEnSituacionVulnerableService vPersonaEnSituacionVulnerableService, TarjetaPersonaEnSituacionVulnerableService vTarjetaPersonaEnSituacionVulnerableService, ColaboradorService vColaboradorService, CargaOfertaCreator vCargaOfertaCreator, DistribucionViandasCreator vDistribucionViandasCreator, DonacionDineroCreator vDonacionDineroCreator, DonacionViandaCreator vDonacionViandaCreator, HacerseCargoDeHeladeraCreator vHacerseCargoDeHeladeraCreator, RegistroDePersonaEnSituacionVulnerableCreator vRegistroDePersonaEnSituacionVulnerableCreator) {
        ofertaService = vOfertaService;
        heladeraService = vHeladeraService;
        personaEnSituacionVulnerableService = vPersonaEnSituacionVulnerableService;
        tarjetaPersonaEnSituacionVulnerableService = vTarjetaPersonaEnSituacionVulnerableService;
        colaboradorService = vColaboradorService;
        cargaOfertaCreator = vCargaOfertaCreator;
        distribucionViandasCreator = vDistribucionViandasCreator;
        donacionDineroCreator = vDonacionDineroCreator;
        donacionViandaCreator = vDonacionViandaCreator;
        hacerseCargoDeHeladeraCreator = vHacerseCargoDeHeladeraCreator;
        registroDePersonaEnSituacionVulnerableCreator = vRegistroDePersonaEnSituacionVulnerableCreator;
    }

    @GetMapping("/colaborar-personas-fisicas")
    public String mostrarColaborarPersonasFisicas(Model model) {
        setPaginaActual("/colaborar", model);

        return "colaborar-personas-fisicas";
    }

    @GetMapping("/colaborar-personas-juridicas")
    public String mostrarColaborarPersonasJuridicas(Model model) {
        setPaginaActual("/colaborar", model);

        return "colaborar-personas-juridicas";
    }

    @GetMapping("/cargar-premio")
    public String mostrarFormCargaOferta(Model model) {
        setPaginaActual("/colaborar", model);
        model.addAttribute("oferta", new Oferta());

        return "cargar-premio";
    }

    @PostMapping("/cargar-premio/guardar")
    public String guardarCargaOferta(@ModelAttribute Oferta oferta) {
        ofertaService.guardarOferta(oferta);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        colaboradorService.colaborar(colaborador.getId(), cargaOfertaCreator, LocalDateTime.now(), oferta);

        return "redirect:/cargar-premio";
    }

    @GetMapping("/distribuir-viandas")
    public String mostrarFormDistribucionViandas(Model model) {
        setPaginaActual("/colaborar", model);
        List<Heladera> heladeras = heladeraService.obtenerHeladeras();
        model.addAttribute("heladeras", heladeras);

        return "distribuir-viandas";
    }

    @PostMapping("/distribuir-viandas/guardar")
    public String guardarDistribucionViandas(
        @RequestParam("heladera-origen") Long heladeraOrigenId,
        @RequestParam("heladera-destino") Long heladeraDestinoId,
        @RequestParam("cantidad") Integer cantidadAMover,
        @RequestParam("motivo") DistribucionViandas.MotivoDistribucion motivo)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Heladera heladeraOrigen = heladeraService.obtenerHeladera(heladeraOrigenId);
        Heladera heladeraDestino = heladeraService.obtenerHeladera(heladeraDestinoId);

        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        colaboradorService.colaborar(colaborador.getId(), distribucionViandasCreator, LocalDateTime.now(), heladeraOrigen, heladeraDestino, cantidadAMover, motivo);

        return "redirect:/distribuir-viandas";
    }

    @GetMapping("/donar-dinero")
    public String mostrarFormDonacionDinero(Model model) {
        setPaginaActual("/colaborar", model);

        return "donar-dinero";
    }

    @PostMapping("/donar-dinero/guardar")
    public String guardarDonacionDinero(
        @RequestParam("monto") Double monto,
        @RequestParam("frecuencia") DonacionDinero.FrecuenciaDePago frecuencia)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        colaboradorService.colaborar(colaborador.getId(), donacionDineroCreator, LocalDateTime.now(), monto, frecuencia);

        return "redirect:/donar-dinero";
    }

    @GetMapping("/donar-vianda")
    public String mostrarFormDonacionVianda(Model model) {
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        colaboradorService.colaborar(colaborador.getId(), donacionViandaCreator, LocalDateTime.now(), vianda, heladera);

        return "redirect:/donar-vianda";
    }

    @GetMapping("/colocar-heladera")
    public String mostrarFormHacerseCargoDeHeladera(Model model) {
        setPaginaActual("/colaborar", model);
        model.addAttribute("heladera", new Heladera());

        return "colocar-heladera";
    }

    @PostMapping("/colocar-heladera/guardar")
    public String guardarHacerseCargoDeHeladera(
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        colaboradorService.colaborar(colaborador.getId(), hacerseCargoDeHeladeraCreator, LocalDateTime.now(), heladera);

        return "redirect:/colocar-heladera";
    }

    @GetMapping("/registrar-persona-vulnerable")
    public String mostrarFormRegistroDePersonaEnSituacionVulnerable(Model model) {
        setPaginaActual("/colaborar", model);

        return "registrar-persona-vulnerable";
    }

    @PostMapping("/registrar-persona-vulnerable/guardar")
    public String guardarRegistroDePersonaEnSituacionVulnerable(
        @RequestParam("nombre") String nombre,
        @RequestParam("apellido") String apellido,
        @RequestParam("fecha-nacimiento") LocalDate fechaNacimiento,
        @RequestParam("tipo-documento") Documento.TipoDocumento tipoDocumento,
        @RequestParam("numero-documento") String numeroDocumento,
        @RequestParam("sexo-documento") Documento.Sexo sexoDocumento,
        @RequestParam(value = "posee-domicilio", defaultValue = "false") Boolean poseeDomicilio,
        @RequestParam(value = "calle", defaultValue = "sin calle") String calle,
        @RequestParam(value = "altura", defaultValue = "sin altura") String altura,
        @RequestParam(value = "ciudad", defaultValue = "sin ciudad") String ciudad,
        @RequestParam(value = "codigo-postal", defaultValue = "sin código postal") String codigoPostal,
        @RequestParam(value = "menores-a-cargo", defaultValue = "0") Integer menoresACargo)
    {
        Documento documento = new Documento(tipoDocumento, numeroDocumento, sexoDocumento);
        PersonaFisica personaFisica = new PersonaFisica(nombre, apellido, documento, fechaNacimiento);

        Ubicacion domicilio = poseeDomicilio ? new Ubicacion(null, null, (calle + " " + altura), codigoPostal, ciudad, "Argentina") : null; // TODO: Hay que arreglar esto, porque cuando ponemos que no tiene domicilio tira error

        LocalDateTime ahora = LocalDateTime.now();

        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = new PersonaEnSituacionVulnerable(personaFisica, domicilio, ahora, menoresACargo, poseeDomicilio);
        Long personaEnSituacionVulnerableId = personaEnSituacionVulnerableService.guardarPersonaEnSituacionVulnerable(personaEnSituacionVulnerable).getId();

        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = tarjetaPersonaEnSituacionVulnerableService.crearTarjeta(personaEnSituacionVulnerableId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        colaboradorService.colaborar(colaborador.getId(), registroDePersonaEnSituacionVulnerableCreator, ahora, tarjetaPersonaEnSituacionVulnerable);

        return "redirect:/registrar-persona-vulnerable";
    }

    public void setPaginaActual(String pagina, Model model) {
        model.addAttribute("paginaActual", pagina);
    }
}
