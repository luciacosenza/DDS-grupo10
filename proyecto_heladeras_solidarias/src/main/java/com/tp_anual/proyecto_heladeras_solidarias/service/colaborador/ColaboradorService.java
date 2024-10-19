package com.tp_anual.proyecto_heladeras_solidarias.service.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionDesperfecto;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionViandasMax;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionViandasMin;
import com.tp_anual.proyecto_heladeras_solidarias.repository.colaborador.ColaboradorRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;

@Service
@Log
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;
    private final ContribucionServiceSelector contribucionServiceSelector;
    private final UsuarioService usuarioService;
    private final OfertaService ofertaService;
    private final HeladeraService heladeraService;

    private final Map<Class<? extends Colaborador>, Set<Class<? extends ContribucionCreator>>> contribucionesPermitidas = new HashMap<>();

    public ColaboradorService(ColaboradorRepository vColaboradorRepository, ContribucionServiceSelector vContribucionServiceSelector, UsuarioService vUsuarioService, OfertaService vOfertaService, HeladeraService vHeladeraService) {
        colaboradorRepository = vColaboradorRepository;
        contribucionServiceSelector = vContribucionServiceSelector;
        usuarioService = vUsuarioService;
        ofertaService = vOfertaService;
        heladeraService = vHeladeraService;

        Set<Class<? extends ContribucionCreator>> contribucionesPermitidasHumano = new HashSet<>();
        contribucionesPermitidasHumano.add(DistribucionViandasCreator.class);
        contribucionesPermitidasHumano.add(DonacionDineroCreator.class);
        contribucionesPermitidasHumano.add(DonacionViandaCreator.class);
        contribucionesPermitidasHumano.add(RegistroDePersonaEnSituacionVulnerableCreator.class);

        Set<Class<? extends ContribucionCreator>> contribucionesPermitidasJuridico = new HashSet<>();
        contribucionesPermitidasJuridico.add(CargaOfertaCreator.class);
        contribucionesPermitidasJuridico.add(DonacionDineroCreator.class);
        contribucionesPermitidasJuridico.add(HacerseCargoDeHeladeraCreator.class);

        contribucionesPermitidas.put(ColaboradorHumano.class, contribucionesPermitidasHumano);
        contribucionesPermitidas.put(ColaboradorJuridico.class, contribucionesPermitidasJuridico);
    }

    public Colaborador obtenerColaborador(Long colaboradorId) {
        return colaboradorRepository.findById(colaboradorId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ColaboradorHumano obtenerColaboradorHumano(Long colaboradorId) {
        return (ColaboradorHumano) obtenerColaborador(colaboradorId);
    }

    public ColaboradorJuridico obtenerColaboradorJuridico(Long colaboradorId) {
        return (ColaboradorJuridico) obtenerColaborador(colaboradorId);
    }

    public List<Colaborador> obtenerColaboradores() {
        return new ArrayList<>(colaboradorRepository.findAll());
    }

    public Colaborador guardarColaborador(Colaborador colaborador) {
        return colaboradorRepository.save(colaborador);
    }

    public ColaboradorHumano guardarColaboradorHumano(ColaboradorHumano colaborador) {
        return (ColaboradorHumano) guardarColaborador(colaborador);
    }

    public ColaboradorJuridico guardarColaboradorJuridico(ColaboradorJuridico colaborador) {
        return (ColaboradorJuridico) guardarColaborador(colaborador);
    }

    public Colaborador asignarUsuario(Long colaboradorId, Usuario usuario) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);
        colaborador.setUsuario(usuario);

        return guardarColaborador(colaborador);
    }

    public void agregarMedioDeContacto(Long colaboradorId, MedioDeContacto medioDeContacto) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);
        colaborador.agregarMedioDeContacto(medioDeContacto);
        guardarColaborador(colaborador);    // Al guardar el colaborador, se guarda el medio de contacto por cascada
    }

    public void agregarContribucion(Long colaboradorId, Contribucion contribucion) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);
        colaborador.agregarContribucion(contribucion);
        guardarColaborador(colaborador);    // Al guardar el colaborador, se guarda la contribución por cascada
    }

    public void agregarBeneficio(Long colaboradorId, Oferta oferta) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);
        colaborador.agregarBeneficio(oferta);
        guardarColaborador(colaborador);
    }

    public Boolean esContribucionPermitida(Long colaboradorId, Class<? extends ContribucionCreator> creatorClass) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);

        return contribucionesPermitidas.get(colaborador.getClass()).contains(creatorClass);
    }

    public Contribucion colaborar(Long colaboradorId, ContribucionCreator creator, LocalDateTime fechaContribucion /* Generalmente LocalDateTime.now() */, Object... args) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);

        if (!esContribucionPermitida(colaboradorId, creator.getClass())) {
            log.log(Level.SEVERE, I18n.getMessage("colaborador.Colaborador.colaborar_err", creator.getClass().getSimpleName(), colaborador.getPersona().getNombre(2), colaborador.getPersona().getTipoPersona()));
            throw new IllegalArgumentException(I18n.getMessage("colaborador.Colaborador.colaborar_exception"));
        }

        Contribucion contribucion = creator.crearContribucion(colaborador, fechaContribucion, false, args);
        ContribucionService contribucionService = contribucionServiceSelector.obtenerContribucionService(contribucion.getClass());
        Long contribucionId = contribucionService.guardarContribucion(contribucion).getId();    // Guardo la contribución para obtener el id
        contribucionService.validarIdentidad(contribucionId, colaborador);

        agregarContribucion(colaboradorId, contribucion);

        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.colaborar_info", contribucion.getClass().getSimpleName(), colaborador.getPersona().getNombre(2)));

        return contribucion;
    }

    // Este es el método correspondiente a confirmar la ejecución / llevada a cabo de una Contribución
    public void confirmarContribucion(Long colaboradorId, Contribucion contribucion, LocalDateTime fechaContribucion /* generalmente LocalDateTime.now() */) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);

        ContribucionService contribucionService = contribucionServiceSelector.obtenerContribucionService(contribucion.getClass());
        contribucionService.confirmar(contribucion.getId(), fechaContribucion);
        contribucionService.guardarContribucion(contribucion);  // Guardo la contribución como "double check", porque al guardar el colaborador esta se guardará por cascada

        guardarColaborador(colaborador);

        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.confirmarContribucion_info", contribucion.getClass().getSimpleName(), colaborador.getPersona().getNombre(2)));
    }

    public void intentarAdquirirBeneficio(Long colaboradorId, Oferta oferta) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);

        // Primero chequea tener los puntos suficientes
        ofertaService.validarPuntos(oferta.getId(), colaborador.getPuntos());

        agregarBeneficio(colaboradorId, oferta);

        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.adquirirBeneficio_info", oferta.getNombre(), colaborador.getPersona().getNombre(2)));
    }

    public void reportarFallaTecnica(Long colaboradorId, Heladera heladera, String descripcion, String foto) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);

        heladeraService.producirFallaTecnica(heladera.getId(), colaborador, descripcion, foto);
    }

    public TarjetaColaborador agregarTarjeta(Long colaboradorId, TarjetaColaborador tarjetaColaborador) {
        ColaboradorHumano colaborador = obtenerColaboradorHumano(colaboradorId);
        colaborador.agregarTarjeta(tarjetaColaborador);

        return guardarColaboradorHumano(colaborador).getTarjeta();
    }

    public void agregarSuscripcion(Long colaboradorId, Suscripcion suscripcion) {
        ColaboradorHumano colaborador = obtenerColaboradorHumano(colaboradorId);
        colaborador.agregarSuscripcion(suscripcion);
        guardarColaborador(colaborador);    // Al guardar el colaborador, se guarda/actualiza la suscripción por cascada
    }

    public void eliminarSuscripcion(Long colaboradorId, Suscripcion suscripcion) {
        ColaboradorHumano colaborador = obtenerColaboradorHumano(colaboradorId);
        colaborador.eliminarSuscripcion(suscripcion);
        guardarColaborador(colaborador);    // Al guardar el colaborador, se elimina la suscripción por orphanRemoval
    }

    public Suscripcion suscribirse(Long colaboradorId, Heladera heladera, MedioDeContacto medioDeContacto, Suscripcion.CondicionSuscripcion condicionSuscripcion, Integer valor) {
        ColaboradorHumano colaborador = obtenerColaboradorHumano(colaboradorId);

        Suscripcion suscripcion;
        switch(condicionSuscripcion) {

            case VIANDAS_MIN -> suscripcion = new SuscripcionViandasMin(colaborador, heladera, medioDeContacto, valor);

            case VIANDAS_MAX -> suscripcion = new SuscripcionViandasMax(colaborador, heladera, medioDeContacto, valor);

            case DESPERFECTO -> suscripcion = new SuscripcionDesperfecto(colaborador, heladera, medioDeContacto);

            default -> {
                log.log(Level.SEVERE, I18n.getMessage("colaborador.ColaboradorHumano.suscribirse_err", colaborador.getPersona().getNombre(2)));
                throw new IllegalArgumentException(I18n.getMessage("colaborador.ColaboradorHumano.suscribirse_exception"));
            }

        }

        agregarSuscripcion(colaboradorId, suscripcion);

        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.agregarSuscripcion_info", colaborador.getPersona().getNombre(2), suscripcion.getHeladera().getNombre()));

        return suscripcion;
    }

    public void modificarSuscripcion(Long colaboradorId, Suscripcion suscripcion, Integer nuevoValor) {
        ColaboradorHumano colaborador = obtenerColaboradorHumano(colaboradorId);

        if (!colaborador.getSuscripciones().contains(suscripcion)) {
            log.log(Level.SEVERE, I18n.getMessage("colaborador.ColaboradorHumano.modificarSuscripcion_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("colaborador.ColaboradorHumano.modificarSuscripcion_exception"));
        }

        switch(suscripcion) {

            case SuscripcionViandasMin suscripcionViandasMin -> {
                suscripcionViandasMin.setViandasDisponiblesMin(nuevoValor);
            }

            case SuscripcionViandasMax suscripcionViandasMax -> {
                suscripcionViandasMax.setViandasParaLlenarMax(nuevoValor);
            }

            default -> {}

        }

        guardarColaborador(colaborador);    // Al guardar el colaborador, se actualiza la suscripción por cascada

        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.modificarSuscripcion_info", suscripcion.getHeladera().getNombre(), colaborador.getPersona().getNombre(2)));
    }

    public void cancelarSuscripcion(Long colaboradorId, Suscripcion suscripcion) {
        ColaboradorHumano colaborador = obtenerColaboradorHumano(colaboradorId);

        eliminarSuscripcion(colaboradorId, suscripcion);

        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.cancelarSuscripcion_info", suscripcion.getHeladera().getNombre(), colaborador.getPersona().getNombre(2)));
    }
}
