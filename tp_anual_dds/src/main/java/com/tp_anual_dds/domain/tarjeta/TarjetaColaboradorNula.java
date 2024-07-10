package com.tp_anual_dds.domain.tarjeta;

import java.util.ArrayList;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoNoAplica;
import com.tp_anual_dds.domain.estados_de_solicitud.EstadoSolicitud;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva.MotivoSolicitud;
import com.tp_anual_dds.domain.tarjeta.permisos_de_apertura.PermisoAperturaNulo;

public class TarjetaColaboradorNula extends TarjetaColaborador {
    public TarjetaColaboradorNula() {
        codigo = "N/A";
        usos = new ArrayList<>();
        titular = null;
        estadoSolicitud = new EstadoNoAplica();
        permiso = new PermisoAperturaNulo();
    }

    @Override
    public ColaboradorHumano getTitular() {
        throw new UnsupportedOperationException("Tarjeta Nula no tiene un titular.");
    }

    @Override
    public void setEstadoSolicitud(EstadoSolicitud vEstadoSolicitud) {}

    @Override
    public Boolean puedeUsar() {
        return false;
    }

    @Override
    public void revocarPermisos() {}

    @Override
    public void solicitarApertura(MotivoSolicitud motivo, Heladera heladeraInvolucrada) {}

    @Override
    public void intentarApertura(Heladera heladeraAAbrir) {}
}
