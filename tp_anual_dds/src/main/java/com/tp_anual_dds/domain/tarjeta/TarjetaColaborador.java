package com.tp_anual_dds.domain.tarjeta;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.estado_de_solicitud.EstadoSolicitud;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoApertura;

public abstract class TarjetaColaborador extends Tarjeta {
    protected ColaboradorHumano titular;
    protected EstadoSolicitud estadoSolicitud;
    protected PermisoApertura permiso;

    @Override
    public abstract ColaboradorHumano getTitular();

    public abstract EstadoSolicitud getEstadoSolicitud();

    public abstract PermisoApertura getPermiso();

    public abstract void setEstadoSolicitud(EstadoSolicitud vEstadoSolicitud);

    public abstract void programarRevocacionPermisos();

    public abstract SolicitudAperturaColaborador solicitarApertura(HeladeraActiva heladeraInvolucrada, SolicitudAperturaColaborador.MotivoSolicitud motivo);

    @Override
    public abstract AperturaColaborador intentarApertura(HeladeraActiva heladeraAAbrir) throws InterruptedException;
}
