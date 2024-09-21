package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
public class GestorDeAperturas {
    private final HeladeraActiva heladera;

    public GestorDeAperturas(HeladeraActiva vHeladera) {
        heladera = vHeladera;
    }
    
    public void revisarMotivoApertura(MotivoSolicitud motivo) {
        if (motivo == MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION &&
            heladera.estaVacia()) {
            
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_err_heladera_vacia", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_vacia"));
        }

        if ((motivo == MotivoSolicitud.INGRESAR_DONACION ||
            motivo == MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION) &&
            heladera.estaLlena()) {

            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_err_heladera_llena", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_llena"));
        }
    }
    
    public void revisarPermisoAperturaC(ColaboradorHumano colaborador) {
        TarjetaColaborador tarjetaColaborador = colaborador.getTarjeta();
        ArrayList<PermisoApertura> permisos = tarjetaColaborador.getPermisos();

        PermisoApertura permisoARevisar = permisos.stream()
            .filter(permiso -> permiso.esHeladeraPermitida(heladera))
            .max(Comparator.comparing(PermisoApertura::getFechaOtorgamiento))
            .orElse(null);

        if (permisoARevisar == null || !permisoARevisar.getOtorgado()) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_err", colaborador.getPersona().getNombre(2), heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_exception"));
        }
    }

    public void revisarPermisoAperturaP(PersonaEnSituacionVulnerable personaEnSituacionVulnerable) {
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = personaEnSituacionVulnerable.getTarjeta();
        
        if (!tarjetaPersonaEnSituacionVulnerable.puedeUsar()) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_err_usos_agotados", personaEnSituacionVulnerable.getPersona().getNombre(2)));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_usos_agotados"));
        }

        if(heladera.estaVacia()) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.resvisarPermisoAperturaP_err_heladera_vacia", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_heladera_vacia"));
        }
    }
}