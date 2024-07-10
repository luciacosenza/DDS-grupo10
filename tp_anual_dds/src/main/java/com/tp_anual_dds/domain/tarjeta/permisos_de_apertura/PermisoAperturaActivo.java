package com.tp_anual_dds.domain.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.Heladera;

public class PermisoAperturaActivo extends PermisoApertura {
    public PermisoAperturaActivo() {
        heladeraPermitida = null;
        fechaOtorgamiento = LocalDateTime.now();
    }

    @Override
    public Heladera getHeladeraPermitida() {
        return heladeraPermitida;
    }

    @Override
    public LocalDateTime getFechaOtorgamiento() {
        return fechaOtorgamiento;
    }

    @Override
    public void actualizarFechaOtorgamiento(){
        fechaOtorgamiento = LocalDateTime.now();
    }

    @Override
    public Boolean esHeladeraPermitida(Heladera heladera) {
        return heladera == heladeraPermitida;
    }

    @Override
    public void setHeladeraPermitida(Heladera vHeladera) {
        heladeraPermitida = vHeladera;
    }

    @Override
    public void resetHeladeraPermitida() {
        heladeraPermitida = null;
    }
}
