package com.tp_anual_dds.domain.tarjeta;

import java.util.ArrayList;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva.MotivoSolicitud;

public abstract class TarjetaColaborador extends Tarjeta {
    protected ColaboradorHumano titular;
    protected SolicitudApertura solicitud;

    public abstract ColaboradorHumano getTitular();

    public abstract void setSolicitud(SolicitudApertura vSolicitud);

    public abstract void solicitarApertura(MotivoSolicitud motivo, ArrayList<Heladera> heladerasInvolucradas);
    
    public abstract void registrarSolicitudApertura(MotivoSolicitud motivo, ArrayList<Heladera> heladerasInvolucradas);
}
