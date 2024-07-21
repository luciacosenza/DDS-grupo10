package com.tp_anual_dds.domain.estado_de_solicitud;

import com.tp_anual_dds.domain.tarjeta.TarjetaColaborador;

public interface EstadoSolicitud {
    public void manejar(TarjetaColaborador tarjeta);
}