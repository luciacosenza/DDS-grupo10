package com.tp_anual_dds.broker;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;

public class MensajeSolicitudApertura implements Mensaje {
    private final HeladeraActiva heladera;
    private final MotivoSolicitud motivo;

    public MensajeSolicitudApertura(HeladeraActiva vHeladera, MotivoSolicitud vMotivo) {
        heladera = vHeladera;
        motivo = vMotivo;
    }
    
    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public MotivoSolicitud getMotivo() {
        return motivo;
    }
    
    @Override
    public void procesar() {
        heladera.getGestorDeAperturas().revisarSolicitudApertura(motivo);
    }

}
