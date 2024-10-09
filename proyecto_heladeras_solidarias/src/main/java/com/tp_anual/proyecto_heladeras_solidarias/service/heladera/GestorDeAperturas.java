package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.PermisoAperturaService;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Log
public class GestorDeAperturas {
    private final HeladeraService heladeraService;
    private final ColaboradorService colaboradorService;
    private final PermisoAperturaService permisoAperturaService;
    private final PersonaEnSituacionVulnerableService personaEnSituacionVulnerableService;

    public GestorDeAperturas(HeladeraService vHeladeraService, ColaboradorService vColaboradorService, PermisoAperturaService vPermisoAperturaService, PersonaEnSituacionVulnerableService vPersonaEnSituacionVulnerableService) {
        heladeraService = vHeladeraService;
        colaboradorService = vColaboradorService;
        permisoAperturaService = vPermisoAperturaService;
        personaEnSituacionVulnerableService = vPersonaEnSituacionVulnerableService;
    }
    
    public void revisarMotivoApertura(Long heladeraId, MotivoSolicitud motivo) {
        HeladeraActiva heladera = heladeraService.obtenerHeladera(heladeraId);

        if (motivo == MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION &&
            heladeraService.estaVacia(heladeraId)) {
            
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_err_heladera_vacia", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_vacia"));
        }

        if ((motivo == MotivoSolicitud.INGRESAR_DONACION ||
            motivo == MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION) &&
            heladeraService.estaLlena(heladeraId)) {

            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_err_heladera_llena", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_llena"));
        }
    }
    
    public void revisarPermisoAperturaC(Long heladeraId, Long colaboradorId) {
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);
        HeladeraActiva heladera = heladeraService.obtenerHeladera(heladeraId);

        TarjetaColaborador tarjetaColaborador = colaborador.getTarjeta();
        ArrayList<PermisoApertura> permisos = tarjetaColaborador.getPermisos();

        PermisoApertura permisoARevisar = permisos.stream()
            .filter(permiso -> permisoAperturaService.esHeladeraPermitida(permiso.getId(), heladeraId))
            .max(Comparator.comparing(PermisoApertura::getFechaOtorgamiento))
            .orElse(null);

        if (permisoARevisar == null || !permisoARevisar.getOtorgado()) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_err", colaborador.getPersona().getNombre(2), heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_exception"));
        }
    }

    public void revisarPermisoAperturaP(Long heladeraId, Long personaEnSituacionVulnerableId) {
        HeladeraActiva heladera = heladeraService.obtenerHeladera(heladeraId);
        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = personaEnSituacionVulnerableService.obtenerPersonaEnSituacionVulnerable(personaEnSituacionVulnerableId);
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = personaEnSituacionVulnerable.getTarjeta();
        
        if (!tarjetaPersonaEnSituacionVulnerable.puedeUsar()) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_err_usos_agotados", personaEnSituacionVulnerable.getPersona().getNombre(2)));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_usos_agotados"));
        }

        if(heladeraService.estaVacia(heladeraId)) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.resvisarPermisoAperturaP_err_heladera_vacia", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_heladera_vacia"));
        }
    }
}