package com.tp_anual_dds.domain.heladera;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.estado_de_solicitud.EstadoRealizada;
import com.tp_anual_dds.domain.estado_de_solicitud.EstadoSolicitud;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;
import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaborador;
import com.tp_anual_dds.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoApertura;

public class GestorDeAperturas {
    private final HeladeraActiva heladera;

    public GestorDeAperturas(HeladeraActiva vHeladera) {
        heladera = vHeladera;
    }

    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public void revisarSolicitudApertura(MotivoSolicitud motivo) {
        if (motivo == MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION &&
            heladera.estaVacia())
            
            throw new UnsupportedOperationException("La heladera " + heladera.getNombre() + " se encuentra vacía");

        if ((motivo == MotivoSolicitud.INGRESAR_DONACION ||
            motivo == MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION) &&
            heladera.estaLlena())

            throw new UnsupportedOperationException("La heladera " + heladera.getNombre() + " se encuentra llena");
    }
    
    public void revisarPermisoAperturaC(ColaboradorHumano colaborador) {
        TarjetaColaborador tarjetaColaborador = colaborador.getTarjeta();
        EstadoSolicitud estadoSolicitud = tarjetaColaborador.getEstadoSolicitud();
        PermisoApertura permisoApertura = tarjetaColaborador.getPermiso();

        if (!(estadoSolicitud instanceof EstadoRealizada) ||
            !(permisoApertura.esHeladeraPermitida(heladera)))
            
                throw new UnsupportedOperationException("No cuenta con los permisos para abrir la heladera " + heladera.getNombre());
    }

    public void revisarPermisoAperturaP(PersonaEnSituacionVulnerable personaEnSituacionVulnerable) {
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = personaEnSituacionVulnerable.getTarjeta();
        
        if (!tarjetaPersonaEnSituacionVulnerable.puedeUsar())
            throw new UnsupportedOperationException("Ya agotó los usos diarios de su tarjeta");

        if(heladera.estaVacia())
            throw new UnsupportedOperationException("La heladera " + heladera.getNombre() + " se encuentra vacía");
    }
}