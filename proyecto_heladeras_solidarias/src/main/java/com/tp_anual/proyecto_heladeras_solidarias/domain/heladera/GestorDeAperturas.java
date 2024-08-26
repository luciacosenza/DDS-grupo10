package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoRealizada;
import com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud.EstadoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.permisos_de_apertura.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class GestorDeAperturas {
    private static final Logger logger = Logger.getLogger(GestorDeAperturas.class.getName());
    private final HeladeraActiva heladera;

    public GestorDeAperturas(HeladeraActiva vHeladera) {
        heladera = vHeladera;
    }

    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public void revisarSolicitudApertura(MotivoSolicitud motivo) {
        if (motivo == MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION &&
            heladera.estaVacia()) {
            
            logger.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_err_heladera_vacia", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_vacia"));
        }

        if ((motivo == MotivoSolicitud.INGRESAR_DONACION ||
            motivo == MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION) &&
            heladera.estaLlena()) {

            logger.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_err_heladera_llena", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_llena"));
        }
    }
    
    public void revisarPermisoAperturaC(ColaboradorHumano colaborador) {
        TarjetaColaborador tarjetaColaborador = colaborador.getTarjeta();
        EstadoSolicitud estadoSolicitud = tarjetaColaborador.getEstadoSolicitud();
        PermisoApertura permisoApertura = tarjetaColaborador.getPermiso();

        if (!(estadoSolicitud instanceof EstadoRealizada) ||
            !(permisoApertura.esHeladeraPermitida(heladera))) {
            
            logger.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_err", colaborador.getPersona().getNombre(2), heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_exception"));
        }
    }

    public void revisarPermisoAperturaP(PersonaEnSituacionVulnerable personaEnSituacionVulnerable) {
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = personaEnSituacionVulnerable.getTarjeta();
        
        if (!tarjetaPersonaEnSituacionVulnerable.puedeUsar()) {
            logger.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_err_usos_agotados", personaEnSituacionVulnerable.getPersona().getNombre(2)));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_usos_agotados"));
        }

        if(heladera.estaVacia()) {
            logger.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.resvisarPermisoAperturaP_err_heladera_vacia", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_heladera_vacia"));
        }
    }
}