package com.tp_anual_dds.domain.tarjeta;

public class EstadoPosible implements EstadoSolicitud {
    @Override
    public void manejar(Solicitud solicitud) {
        solicitud.setEstado(new EstadoPendiente());
    }
}
