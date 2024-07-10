package com.tp_anual_dds.domain.tarjeta;

public class SolicitudApertura extends Solicitud {
    public SolicitudApertura(EstadoSolicitud vEstado) {
        estado = vEstado;
    }

    @Override
    public void ejecutar() {
        estado.manejar(this);
    }
}
