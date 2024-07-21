package com.tp_anual_dds.domain.estado_de_solicitud;

import com.tp_anual_dds.domain.tarjeta.TarjetaColaborador;

public class EstadoNoAplica implements EstadoSolicitud {
    @Override
    public void manejar(TarjetaColaborador tarjeta) {}
}
