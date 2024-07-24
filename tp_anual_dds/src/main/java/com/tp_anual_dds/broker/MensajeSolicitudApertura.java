package com.tp_anual_dds.broker;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;

public class MensajeSolicitudApertura implements Mensaje {
    private HeladeraActiva heladera;
    private MotivoSolicitud motivoSolicitud;

    public MensajeSolicitudApertura(HeladeraActiva vHeladera, MotivoSolicitud vMotivoSolicitud) {
        heladera = vHeladera;
        motivoSolicitud = vMotivoSolicitud;
    }
    
    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public MotivoSolicitud getMotivoSolicitud() {
        return motivoSolicitud;
    }
    
    @Override
    public void procesar() {
        heladera.getGestorDeAperturas().revisarSolicitudApertura(motivoSolicitud);
    }

}
