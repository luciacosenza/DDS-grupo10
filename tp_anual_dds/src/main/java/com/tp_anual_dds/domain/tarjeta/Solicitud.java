package com.tp_anual_dds.domain.tarjeta;

public abstract class Solicitud {
    protected EstadoSolicitud estado;

    public void setEstado(EstadoSolicitud vEstado) {
        estado = vEstado;
    }

    public abstract void ejecutar();
}
