package com.tp_anual.proyecto_heladeras_solidarias.service.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.ContribucionCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionDesperfecto;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionViandasMax;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionViandasMin;
import com.tp_anual.proyecto_heladeras_solidarias.repository.colaborador.ColaboradorRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.MedioDeContactoRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.HeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.oferta.OfertaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.suscripcion.SuscripcionRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Level;

@Log
@Service
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;
    private final ContribucionRepository contribucionRepository;
    private final OfertaRepository ofertaRepository;
    private final HeladeraRepository heladeraRepository;
    private final MedioDeContactoRepository medioDeContactoRepository;
    private final SuscripcionRepository suscripcionRepository;
    private final ContribucionService contribucionService;
    private final OfertaService ofertaService;
    private final HeladeraService heladeraService;

    public ColaboradorService(ColaboradorRepository vColaboradorRepository, ContribucionRepository vContribucionRepository, OfertaRepository vOfertaRepository, HeladeraRepository vHeladeraRepository, MedioDeContactoRepository vMedioDeContactoRepository, SuscripcionRepository vSuscripcionRepository, ContribucionService vContribucionService, OfertaService vOfertaService, HeladeraService vHeladeraService) {
        colaboradorRepository = vColaboradorRepository;
        contribucionRepository = vContribucionRepository;
        ofertaRepository = vOfertaRepository;
        heladeraRepository = vHeladeraRepository;
        medioDeContactoRepository = vMedioDeContactoRepository;
        suscripcionRepository = vSuscripcionRepository;
        contribucionService = vContribucionService;
        ofertaService = vOfertaService;
        heladeraService = vHeladeraService;
    }

    public Contribucion colaborar(Long colaboradorId, ContribucionCreator creator, LocalDateTime fechaContribucion /* generalmente LocalDateTime.now() */, Object... args) {
        Colaborador colaborador = colaboradorRepository.findById(colaboradorId);

        if (!colaborador.esCreatorPermitido(creator.getClass())) {
            log.log(Level.SEVERE, I18n.getMessage("colaborador.Colaborador.colaborar_err", creator.getClass().getSimpleName(), persona.getNombre(2), persona.getTipoPersona()));
            throw new IllegalArgumentException(I18n.getMessage("colaborador.Colaborador.colaborar_exception"));
        }

        Contribucion contribucion = contribucionService.crearContribucion(creator, colaborador, fechaContribucion, false, args);

        Long contribucionId = contribucionRepository.save(contribucion).getId();
        contribucionService.validarIdentidad(contribucionId);

        colaborador.agregarContribucionPendiente(contribucion);
        colaboradorRepository.save(colaborador);

        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.colaborar_info", contribucion.getClass().getSimpleName(), persona.getNombre(2)));

        return contribucion;
    }

    // Este es el método correspondiente a confirmar la ejecución / llevada a cabo de una Contribución
    public void confirmarContribucion(Long colaboradorId, Long contribucionId, LocalDateTime fechaContribucion /* generalmente LocalDateTime.now() */) {
        Colaborador colaborador = colaboradorRepository.findById(colaboradorId);

        Contribucion contribucion = contribucionRepository.findById(contribucionId);

        contribucionService.confirmar(contribucionId, fechaContribucion);
        contribucionRepository.save(contribucion);

        colaborador.agregarContribucion(contribucion);
        colaborador.eliminarContribucionPendiente(contribucion);
        colaboradorRepository.save(colaborador);

        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.confirmarContribucion_info", contribucion.getClass().getSimpleName(), persona.getNombre(2)));
    }

    public void intentarAdquirirBeneficio(Long colaboradorId, Long ofertaId) {
        Colaborador colaborador = colaboradorRepository.findById(colaboradorId);
        Oferta oferta = ofertaRepository.findById(ofertaId);

        // Primero chequea tener los puntos suficientes
        ofertaService.validarPuntos(ofertaId, colaboradorId);

        colaborador.agregarBeneficio(oferta);
        colaboradorRepository.save(colaborador);

        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.adquirirBeneficio_info", oferta.getNombre(), persona.getNombre(2)));
    }

    public void reportarFallaTecnica(Long colaboradorId, Long heladeraId, String descripcion, String foto) {
        Colaborador colaborador = colaboradorRepository.findById(colaboradorId);
        HeladeraActiva heladera = heladeraRepository.findById(heladeraId);

        heladeraService.producirFallaTecnica(colaboradorId, descripcion, foto);
        heladeraRepository.save(heladera);
    }

    public Suscripcion suscribirse(Long colaboradorId, Long heladeraId, Long medioDeContactoId, Suscripcion.CondicionSuscripcion condicionSuscripcion, Integer valor) {
        ColaboradorHumano colaborador = colaboradorRepository.findById(colaboradorId);
        HeladeraActiva heladera = heladeraRepository.findById(heladeraId);
        MedioDeContacto medioDeContacto = medioDeContactoRepository.findById(medioDeContactoId);

        Suscripcion suscripcion;
        switch(condicionSuscripcion) {

            case VIANDAS_MIN -> suscripcion = new SuscripcionViandasMin(colaborador, heladera, medioDeContacto, valor);

            case VIANDAS_MAX -> suscripcion = new SuscripcionViandasMax(colaborador, heladera, medioDeContacto, valor);

            case DESPERFECTO -> suscripcion = new SuscripcionDesperfecto(colaborador, heladera, medioDeContacto);

            default -> {
                log.log(Level.SEVERE, I18n.getMessage("heladera.HeladerNula.getUbicacion_err", colaborador.getPersona().getNombre(2)));
                throw new IllegalArgumentException(I18n.getMessage("colaborador.ColaboradorHumano.suscribirse_exception"));
            }

        }

        colaborador.agregarSuscripcion(suscripcion);
        colaboradorRepository.save(colaborador);

        suscripcion.darDeAlta();
        suscripcionRepository.save(suscripcion);

        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.agregarSuscripcion_info", colaborador.getPersona().getNombre(2), suscripcion.getHeladera().getNombre()));

        return suscripcion;
    }

    public void modificarSuscripcion(Long suscripcionId, Integer nuevoValor) {
        Suscripcion suscripcion = suscripcionRepository.findById(suscripcionId);

        switch(suscripcion) {

            case SuscripcionViandasMin suscripcionViandasMin -> {
                suscripcionViandasMin.setViandasDisponiblesMin(nuevoValor);
                suscripcionRepository.save(suscripcionViandasMin);
            }

            case SuscripcionViandasMax suscripcionViandasMax -> {
                suscripcionViandasMax.setViandasParaLlenarMax(nuevoValor);
                suscripcionRepository.save(suscripcionViandasMax);
            }

            default -> {}

        }

        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.modificarSuscripcion_info", suscripcion.getHeladera().getNombre(), persona.getNombre(2)));
    }

    public void cancelarSuscripcion(Long colabarodorId, Long suscripcionId) {
        ColaboradorHumano colaborador = colaboradorRepository.findById(colabarodorId);
        Suscripcion suscripcion = suscripcionRepository.findById(suscripcionId);

        colaborador.eliminarSuscripcion(suscripcion);
        colaboradorRepository.save(colaborador);

        suscripcion.darDeBaja();
        suscripcionRepository.delete(suscripcion);

        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.cancelarSuscripcion_info", suscripcion.getHeladera().getNombre(), persona.getNombre(2)));
    }

}
