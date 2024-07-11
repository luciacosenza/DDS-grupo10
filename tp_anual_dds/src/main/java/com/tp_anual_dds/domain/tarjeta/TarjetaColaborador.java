package com.tp_anual_dds.domain.tarjeta;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoSolicitud;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva.MotivoSolicitud;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoApertura;

public abstract class TarjetaColaborador extends Tarjeta {
    protected ColaboradorHumano titular;
    protected EstadoSolicitud estadoSolicitud;
    protected PermisoApertura permiso;
    protected final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    @Override
    public abstract ColaboradorHumano getTitular();

    public abstract void setEstadoSolicitud(EstadoSolicitud vEstadoSolicitud);

    public abstract void programarRevocacionPermisos();

    public abstract void solicitarApertura(MotivoSolicitud motivo, Heladera heladeraInvolucrada);

    public abstract void intentarApertura(Heladera heladeraAAbrir);
}
