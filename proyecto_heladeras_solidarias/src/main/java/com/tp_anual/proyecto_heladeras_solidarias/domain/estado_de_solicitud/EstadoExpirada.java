package com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud;

import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaColaborador;

public class EstadoExpirada implements EstadoSolicitud {
    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        System.out.println("La solicitud previa expiró, haga una nueva");
        tarjeta.setEstadoSolicitud(new EstadoPosible());
    }
}
