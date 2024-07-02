package com.tp_anual_dds.domain.tarjeta;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;

public abstract class TarjetaColaborador extends Tarjeta {
    protected ColaboradorHumano titular;
    protected EstadoSolicitudApertura estadoSolicitud;

    public void setEstadoSolicitud(EstadoSolicitudApertura vEstadoSolicitud) {
        estadoSolicitud = vEstadoSolicitud;
    }
}
