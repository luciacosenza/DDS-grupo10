package com.tp_anual.proyecto_heladeras_solidarias.model.estado_de_solicitud;

import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import lombok.extern.java.Log;

@Log
public class EstadoPosible implements EstadoSolicitud {

    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        tarjeta.setEstadoSolicitud(new EstadoPendiente());
    }
}