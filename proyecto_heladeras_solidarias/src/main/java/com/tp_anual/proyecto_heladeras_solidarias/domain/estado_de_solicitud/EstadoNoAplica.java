package com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud;

import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaColaborador;

public class EstadoNoAplica implements EstadoSolicitud {
    @Override
    public void manejar(TarjetaColaborador tarjeta) {}
}
