package com.tp_anual_dds.domain.tarjeta;

import com.tp_anual_dds.domain.heladera.Heladera;

public interface EstadoSolicitudApertura {
    public void solicitarApertura(TarjetaColaborador tarjeta, Heladera heladera);
}