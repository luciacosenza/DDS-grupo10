package com.tp_anual_dds.domain.estados_de_solicitud;

import com.tp_anual_dds.domain.tarjeta.TarjetaColaborador;

public class EstadoExpirada implements EstadoSolicitud {
    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        System.out.println("La solicitud previa expiró, haga una nueva");
        tarjeta.setEstadoSolicitud(new EstadoPosible());
    }
}
