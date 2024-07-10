package com.tp_anual_dds.domain.tarjeta;

public class EstadoPendiente implements EstadoSolicitud {
    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        throw new UnsupportedOperationException("La solicitud ya está pendiente");
    }
}
