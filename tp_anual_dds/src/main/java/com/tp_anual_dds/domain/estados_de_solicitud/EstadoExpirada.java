package com.tp_anual_dds.domain.estados_de_solicitud;

import com.tp_anual_dds.domain.tarjeta.TarjetaColaborador;

public class EstadoExpirada implements EstadoSolicitud {
    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        throw new UnsupportedOperationException("La solicitud está expirada");
    }
}
