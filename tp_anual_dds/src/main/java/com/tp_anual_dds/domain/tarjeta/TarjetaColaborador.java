package com.tp_anual_dds.domain.tarjeta;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva.MotivoSolicitud;

public abstract class TarjetaColaborador extends Tarjeta {
    protected ColaboradorHumano titular;
    protected EstadoSolicitud estadoSolicitud;
    protected PermisoApertura permiso;
    protected final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    public abstract ColaboradorHumano getTitular();

    public abstract void setEstadoSolicitud(EstadoSolicitud vEstadoSolicitud);

    public abstract void revocarPermisos();

    public abstract void solicitarApertura(MotivoSolicitud motivo, ArrayList<Heladera> heladerasInvolucradas);
}
