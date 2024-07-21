package com.tp_anual_dds.domain.tarjeta;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoNoAplica;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoSolicitud;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoApertura;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoAperturaNulo;

public class TarjetaColaboradorNula extends TarjetaColaborador {
    public TarjetaColaboradorNula() {
        codigo = "N/A";
        titular = null;
        estadoSolicitud = new EstadoNoAplica();
        permiso = new PermisoAperturaNulo();
    }

    @Override
    public ColaboradorHumano getTitular() {
        throw new UnsupportedOperationException("Tarjeta Nula no tiene un titular.");
    }

    @Override
    public PermisoApertura getPermiso() {
        return permiso;
    }

    @Override
    public void setEstadoSolicitud(EstadoSolicitud vEstadoSolicitud) {}

    @Override
    public Boolean puedeUsar() {
        return false;
    }

    @Override
    public void programarRevocacionPermisos() {}

    @Override
    public SolicitudAperturaColaborador solicitarApertura(HeladeraActiva heladeraInvolucrada, SolicitudAperturaColaborador.MotivoSolicitud motivo) {
        throw new UnsupportedOperationException("Tarjeta Nula no puede solicitar aperturas");
    }

    @Override
    public AperturaColaborador intentarApertura(HeladeraActiva heladeraAAbrir) {
        throw new UnsupportedOperationException("Tarjeta Nula no puede ejecutar aperturas");
    }
}
