package com.tp_anual_dds.domain.tarjeta;

public class EstadoPosible implements EstadoSolicitud {
    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        tarjeta.setEstadoSolicitud(new EstadoPendiente());
    }
}
