package com.tp_anual_dds.domain.tarjeta;

public class EstadoExpirada implements EstadoSolicitud {
    @Override
    public void manejar(Solicitud solicitud) {
        throw new UnsupportedOperationException("La solicitud está expirada");
    }
}
