package com.tp_anual_dds.domain.tarjeta;

import java.util.ArrayList;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;

public class TarjetaColaboradorActiva extends TarjetaColaborador {
    public enum MotivoSolicitud {
        INGRESAR_DONACION,
        INGRESAR_LOTE_DE_DISTRIBUCION,
        RETIRAR_LOTE_DE_DISTRIBUCION
    }
    
    public TarjetaColaboradorActiva(String vCodigo, ColaboradorHumano vTitular) {
        codigo = vCodigo;
        usos = new ArrayList<>();
        titular = vTitular;
        solicitud = new SolicitudApertura(new EstadoPosible());
    }

    @Override
    public ColaboradorHumano getTitular() {
        return titular;
    }

    @Override
    public void setSolicitud(SolicitudApertura vSolicitud) {
        solicitud = vSolicitud;
    }

    @Override
    public Boolean puedeUsar() {
        return true; // Se puede definir alguna lógica especifica si es necesario.
    }

    @Override
    public void solicitarApertura(MotivoSolicitud motivo, ArrayList<Heladera> heladerasInvolucradas) {
        solicitud.ejecutar();
        registrarSolicitudApertura(motivo, heladerasInvolucradas);
    }

    @Override
    public void registrarSolicitudApertura(MotivoSolicitud motivo, ArrayList<Heladera> heladerasInvolucradas) {

    }

}
