package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.vianda.PosesionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.vianda.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaboradorNula;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.PosesionViandasService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.ViandaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaPersonaEnSituacionVulnerableService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ContribucionViewController {

    private final HeladeraService heladeraService;
    private final TarjetaColaboradorService tarjetaColaboradorService;
    private final TarjetaPersonaEnSituacionVulnerableService tarjetaPersonaEnSituacionVulnerableService;
    private final PersonaEnSituacionVulnerableService personaEnSituacionVulnerableService;
    private final ViandaService viandaService;
    private final ColaboradorService colaboradorService;
    private final CargaOfertaCreator cargaOfertaCreator;
    private final DistribucionViandasCreator distribucionViandasCreator;
    private final DonacionDineroCreator donacionDineroCreator;
    private final DonacionViandaCreator donacionViandaCreator;
    private final HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator;
    private final RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator;
    private final ContribucionFinder contribucionFinder;
    private final DistribucionViandasService distribucionViandasService;
    private final PosesionViandasService posesionViandasService;

    public ContribucionViewController(HeladeraService vHeladeraService, TarjetaColaboradorService vTarjetaColaboradorService, PersonaEnSituacionVulnerableService vPersonaEnSituacionVulnerableService, ViandaService vViandaService, TarjetaPersonaEnSituacionVulnerableService vTarjetaPersonaEnSituacionVulnerableService, ColaboradorService vColaboradorService, CargaOfertaCreator vCargaOfertaCreator, DistribucionViandasCreator vDistribucionViandasCreator, DonacionDineroCreator vDonacionDineroCreator, DonacionViandaCreator vDonacionViandaCreator, HacerseCargoDeHeladeraCreator vHacerseCargoDeHeladeraCreator, RegistroDePersonaEnSituacionVulnerableCreator vRegistroDePersonaEnSituacionVulnerableCreator, ContribucionFinder vContribucionFinder, DistribucionViandasService vDistribucionViandasService, PosesionViandasService vPosesionViandasService) {
        heladeraService = vHeladeraService;
        tarjetaColaboradorService = vTarjetaColaboradorService;
        personaEnSituacionVulnerableService = vPersonaEnSituacionVulnerableService;
        tarjetaPersonaEnSituacionVulnerableService = vTarjetaPersonaEnSituacionVulnerableService;
        viandaService = vViandaService;
        colaboradorService = vColaboradorService;
        cargaOfertaCreator = vCargaOfertaCreator;
        distribucionViandasCreator = vDistribucionViandasCreator;
        donacionDineroCreator = vDonacionDineroCreator;
        donacionViandaCreator = vDonacionViandaCreator;
        hacerseCargoDeHeladeraCreator = vHacerseCargoDeHeladeraCreator;
        registroDePersonaEnSituacionVulnerableCreator = vRegistroDePersonaEnSituacionVulnerableCreator;
        contribucionFinder = vContribucionFinder;
        distribucionViandasService = vDistribucionViandasService;
        posesionViandasService = vPosesionViandasService;
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
        Heladera heladeraOrigen = heladeraService.obtenerHeladera(heladeraOrigenId);
        Heladera heladeraDestino = heladeraService.obtenerHeladera(heladeraDestinoId);

        if(heladeraService.seVaciaCon(heladeraOrigen.getId(), cantidadAMover - 1)) {
            return "redirect:/donar-vianda";    // Habría que retornar un error/aviso o algo así
        }

        if(heladeraService.seLlenaCon(heladeraDestino.getId(), cantidadAMover + 1)) {
            return "redirect:/donar-vianda";    // Habría que retornar un error/aviso o algo así
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumanoPorUsername(username);
        Long colaboradorId = colaborador.getId();
        colaboradorService.colaborar(colaboradorId, distribucionViandasCreator, LocalDateTime.now(), heladeraOrigen, heladeraDestino, cantidadAMover, motivo);

        if(colaborador.getTarjeta().getClass() == TarjetaColaboradorNula.class) {
            TarjetaColaborador tarjetaColaborador = tarjetaColaboradorService.crearTarjeta(colaboradorId);
            String codigoTarjeta = colaboradorService.agregarTarjeta(colaboradorId, tarjetaColaborador).getCodigo();
        }

        heladeraService.reservarViandas(heladeraOrigenId, cantidadAMover);
        heladeraService.reservarEspacioParaViandas(heladeraDestinoId, cantidadAMover);

        return "redirect:/distribuir-viandas";
    }

    @GetMapping("/donar-dinero")
    public String mostrarFormDonacionDinero(Model model) {
        setPaginaActual("/colaborar", model);
        return "donar-dinero";
    }

    @PostMapping("/donar-dinero/guardar")
    public String guardarDonacionDinero(
        @RequestParam(value = "monto", defaultValue = "0") Double monto,
        @RequestParam(value = "monto-personalizado", defaultValue = "0") Double montoPersonalizado,
        @RequestParam("frecuencia") DonacionDinero.FrecuenciaDePago frecuencia)
    {
        Double montoFinal = Math.max(monto, montoPersonalizado);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        colaboradorService.colaborar(colaborador.getId(), donacionDineroCreator, LocalDateTime.now(), montoFinal, frecuencia);

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

        if(heladeraService.seLlenaCon(heladera.getId(), 1)) {
            return "redirect:/donar-vianda";    // Habría que retornar un error/aviso o algo así
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumanoPorUsername(username);
        Long colaboradorId = colaborador.getId();

        Vianda vianda = new Vianda(comida, colaborador, fechaCaducidad, LocalDateTime.now(), calorias, peso, false);

        colaboradorService.colaborar(colaboradorId, donacionViandaCreator, LocalDateTime.now(), vianda, heladera);

        if(colaborador.getTarjeta().getClass() == TarjetaColaboradorNula.class) {
            TarjetaColaborador tarjetaColaborador = tarjetaColaboradorService.crearTarjeta(colaboradorId);
            String codigoTarjeta = colaboradorService.agregarTarjeta(colaboradorId, tarjetaColaborador).getCodigo();
        }

        heladeraService.reservarEspacioParaViandas(heladeraId, 1);

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
            @RequestParam("capacidad") Integer capacidad,
            @RequestParam("latitud") Double latitud,
            @RequestParam("longitud") Double longitud
    )
    {
        Ubicacion ubicacion = new Ubicacion(latitud, longitud, (calle + " " + altura), codigoPostal, ciudad, "Argentina");
        Heladera heladera = new Heladera(nombre, ubicacion, capacidad, tempMinima, tempMaxima, new ArrayList<>(), null, LocalDateTime.now(), false );
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

    @GetMapping("/contribuciones")
    public String mostrarContribuciones(Model model) {
        setPaginaActual("/contribuciones", model);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);

        model.addAttribute("colaborador", colaborador);

        return "contribuciones";
    }

    @GetMapping("/confirmar-contribucion/{id}")
    public String confirmarContribucion(@PathVariable Long id) {
        Contribucion contribucion = contribucionFinder.obtenerContribucion(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsername(username);
        colaboradorService.confirmarContribucion(colaborador.getId(), contribucion, LocalDateTime.now());

        return "redirect:/contribuciones";
    }

    @GetMapping("/confirmar-ingreso-vianda/{id}")
    public String confirmarIngresoVianda(@PathVariable Long id) {
        DonacionVianda donacionVianda = (DonacionVianda) contribucionFinder.obtenerContribucion(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumanoPorUsername(username);

        Heladera heladera = donacionVianda.getHeladera();
        Vianda vianda = donacionVianda.getVianda();

        heladeraService.agregarVianda(heladera.getId(), vianda);
        viandaService.agregarAHeladeraPrimeraVez(vianda.getId(), heladera);

        colaboradorService.confirmarContribucion(colaborador.getId(), donacionVianda, LocalDateTime.now());

        return "redirect:/contribuciones";
    }

    @GetMapping("/confirmar-retiro-viandas/{id}")
    public String confirmarRetiroViandas(@PathVariable Long id) {
        DistribucionViandas distribucionViandas = (DistribucionViandas) contribucionFinder.obtenerContribucion(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumanoPorUsername(username);

        Heladera heladera = distribucionViandas.getOrigen();
        Integer cantidadViandas = distribucionViandas.getCantidadViandasAMover();

        List<Vianda> viandasACargo = new ArrayList<>();

        for (Integer i = 1; i <= cantidadViandas; i++) {
            Vianda viandaAux = heladeraService.retirarVianda(heladera.getId());
            viandaService.quitarDeHeladera(viandaAux.getId());
            viandasACargo.add(viandaAux);
        }

        PosesionViandas posesionViandas = posesionViandasService.crearPosesionViandas(colaborador, viandasACargo, LocalDateTime.now());
        posesionViandasService.guardarPosesionViandas(posesionViandas); //

        distribucionViandasService.confirmarRetiro(distribucionViandas.getId());

        colaboradorService.retirarViandas(colaborador.getId());

        return "redirect:/contribuciones";
    }

    @GetMapping("/confirmar-ingreso-viandas/{id}")
    public String confirmarIngresoViandas(@PathVariable Long id) {
        DistribucionViandas distribucionViandas = (DistribucionViandas) contribucionFinder.obtenerContribucion(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumanoPorUsername(username);

        Heladera heladera = distribucionViandas.getDestino();
        Integer cantidadViandas = distribucionViandas.getCantidadViandasAMover();

        PosesionViandas posesionViandas = posesionViandasService.obtenerPosesionViandasMasRecientePorColaborador(colaborador);

        // Copio el array en vez de quitar directamente del array de posesionViandas porque quiero que esta se persista correctamente en la posteridad (no sin viandas)
        List<Vianda> viandasACargo = posesionViandas.getViandas();

        for (Integer i = 1; i <= cantidadViandas; i++) {
            Vianda viandaAux = viandasACargo.removeFirst();
            heladeraService.agregarVianda(heladera.getId(), viandaAux);
            viandaService.agregarAHeladera(viandaAux.getId(), heladera);
        }

        colaboradorService.ingresarViandas(colaborador.getId());
        colaboradorService.confirmarContribucion(colaborador.getId(), distribucionViandas, LocalDateTime.now());

        return "redirect:/contribuciones";
    }

    public void setPaginaActual(String pagina, Model model) {
        model.addAttribute("paginaActual", pagina);
    }
}
