package com.tp_anual.proyecto_heladeras_solidarias.service.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.ContribucionCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionDesperfecto;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionViandasMax;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionViandasMin;
import com.tp_anual.proyecto_heladeras_solidarias.repository.colaborador.ColaboradorRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.ContribucionService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.suscripcion.SuscripcionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Level;

@Service
@Log
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;
    private final ContribucionService contribucionService;
    private final OfertaService ofertaService;
    private final HeladeraService heladeraService;
    private final MedioDeContactoService medioDeContactoService;
    private final SuscripcionService suscripcionService;

    public ColaboradorService(ColaboradorRepository vColaboradorRepository, @Qualifier("contribucionService") ContribucionService vContribucionService, OfertaService vOfertaService, HeladeraService vHeladeraService, MedioDeContactoService vMedioDeContactoService, SuscripcionService vSuscripcionService) {
        colaboradorRepository = vColaboradorRepository;
        suscripcionService = vSuscripcionService;
        contribucionService = vContribucionService;
        ofertaService = vOfertaService;
        heladeraService = vHeladeraService;
        medioDeContactoService = vMedioDeContactoService;
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

    public Colaborador guardarColaborador(Colaborador colaborador) {
        return colaboradorRepository.save(colaborador);
    }

    public Boolean esCreatorPermitido(Long colaboradorId, Class<? extends ContribucionCreator> creatorClass) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);

        return colaborador.getCreatorsPermitidos().contains(creatorClass);
    }

    public Contribucion colaborar(Long colaboradorId, ContribucionCreator creator, LocalDateTime fechaContribucion /* generalmente LocalDateTime.now() */, Object... args) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);

        if (!esCreatorPermitido(colaboradorId, creator.getClass())) {
            log.log(Level.SEVERE, I18n.getMessage("colaborador.Colaborador.colaborar_err", creator.getClass().getSimpleName(), colaborador.getPersona().getNombre(2), colaborador.getPersona().getTipoPersona()));
            throw new IllegalArgumentException(I18n.getMessage("colaborador.Colaborador.colaborar_exception"));
        }

        Contribucion contribucion = creator.crearContribucion(colaborador, fechaContribucion, false , args);
        Long contribucionId = contribucionService.guardarContribucion(contribucion).getId();
        contribucionService.validarIdentidad(contribucionId, colaboradorId);

        colaborador.agregarContribucionPendiente(contribucion);
        guardarColaborador(colaborador);

        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.colaborar_info", contribucion.getClass().getSimpleName(), colaborador.getPersona().getNombre(2)));

        return contribucion;
    }

    // Este es el método correspondiente a confirmar la ejecución / llevada a cabo de una Contribución
    public void confirmarContribucion(Long colaboradorId, Long contribucionId, LocalDateTime fechaContribucion /* generalmente LocalDateTime.now() */) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);

        Contribucion contribucion = contribucionService.obtenerContribucion(contribucionId);

        contribucionService.confirmar(contribucionId, colaboradorId, fechaContribucion);
        contribucionService.guardarContribucion(contribucion);

        colaborador.agregarContribucion(contribucion);
        colaborador.eliminarContribucionPendiente(contribucion);
        guardarColaborador(colaborador);

        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.confirmarContribucion_info", contribucion.getClass().getSimpleName(), colaborador.getPersona().getNombre(2)));
    }

    public void intentarAdquirirBeneficio(Long colaboradorId, Long ofertaId) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);
        Oferta oferta = ofertaService.obtenerOferta(ofertaId);

        // Primero chequea tener los puntos suficientes
        ofertaService.validarPuntos(ofertaId, colaboradorId);

        colaborador.agregarBeneficio(oferta);
        guardarColaborador(colaborador);

        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.adquirirBeneficio_info", oferta.getNombre(), colaborador.getPersona().getNombre(2)));
    }

    public void reportarFallaTecnica(Long colaboradorId, Long heladeraId, String descripcion, String foto) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);
        Heladera heladera = heladeraService.obtenerHeladera(heladeraId);

        heladeraService.producirFallaTecnica(heladeraId, colaboradorId, descripcion, foto);
        heladeraService.guardarHeladera(heladera);
    }

    public Suscripcion suscribirse(Long colaboradorId, Long heladeraId, Long medioDeContactoId, Suscripcion.CondicionSuscripcion condicionSuscripcion, Integer valor) {
        ColaboradorHumano colaborador = obtenerColaboradorHumano(colaboradorId);
        Heladera heladera = heladeraService.obtenerHeladera(heladeraId);
        MedioDeContacto medioDeContacto = medioDeContactoService.obtenerMedioDeContacto(medioDeContactoId);

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

        colaborador.agregarSuscripcion(suscripcion);
        guardarColaborador(colaborador);

        suscripcionService.guardarSuscripcion(suscripcion);

        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.agregarSuscripcion_info", colaborador.getPersona().getNombre(2), suscripcion.getHeladera().getNombre()));

        return suscripcion;
    }

    public void modificarSuscripcion(Long colaboradorId, Long suscripcionId, Integer nuevoValor) {
        ColaboradorHumano colaborador = obtenerColaboradorHumano(colaboradorId);
        Suscripcion suscripcion = suscripcionService.obtenerSuscripcion(suscripcionId);

        switch(suscripcion) {

            case SuscripcionViandasMin suscripcionViandasMin -> {
                suscripcionViandasMin.setViandasDisponiblesMin(nuevoValor);
                suscripcionService.guardarSuscripcion(suscripcionViandasMin);
            }

            case SuscripcionViandasMax suscripcionViandasMax -> {
                suscripcionViandasMax.setViandasParaLlenarMax(nuevoValor);
                suscripcionService.guardarSuscripcion(suscripcionViandasMax);
            }

            default -> {}

        }

        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.modificarSuscripcion_info", suscripcion.getHeladera().getNombre(), colaborador.getPersona().getNombre(2)));
    }

    public void cancelarSuscripcion(Long colaboradorId, Long suscripcionId) {
        ColaboradorHumano colaborador = obtenerColaboradorHumano(colaboradorId);
        Suscripcion suscripcion = suscripcionService.obtenerSuscripcion(suscripcionId);

        colaborador.eliminarSuscripcion(suscripcion);
        guardarColaborador(colaborador);

        suscripcionService.eliminarSuscripcion(suscripcion);

        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.cancelarSuscripcion_info", suscripcion.getHeladera().getNombre(), colaborador.getPersona().getNombre(2)));
    }
}
