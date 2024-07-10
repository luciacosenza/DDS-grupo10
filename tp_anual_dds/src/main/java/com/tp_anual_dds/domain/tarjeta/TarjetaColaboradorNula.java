package com.tp_anual_dds.domain.tarjeta;

import java.util.ArrayList;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva.MotivoSolicitud;

public class TarjetaColaboradorNula extends TarjetaColaborador {
    public TarjetaColaboradorNula() {
        codigo = "N/A";
        usos = new ArrayList<>();
        titular = null;
        solicitud = new SolicitudApertura(new EstadoNoAplica());
    }

    @Override
    public ColaboradorHumano getTitular() {
        throw new UnsupportedOperationException("Tarjeta Nula no tiene un titular.");
    }

    @Override
    public void setSolicitud(SolicitudApertura vSolicitud) {}

    @Override
    public Boolean puedeUsar() {
        return false;
    }

    @Override
    public void solicitarApertura(MotivoSolicitud motivo, ArrayList<Heladera> heladerasInvolucradas) {}

    @Override
    public void registrarSolicitudApertura(MotivoSolicitud motivo, ArrayList<Heladera> heladerasInvolucradas) {}
}
