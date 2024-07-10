package com.tp_anual_dds.domain.estados_de_solicitud;

import com.tp_anual_dds.domain.tarjeta.TarjetaColaborador;

public class EstadoPosible implements EstadoSolicitud {
    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        tarjeta.setEstadoSolicitud(new EstadoPendiente());
    }
}
