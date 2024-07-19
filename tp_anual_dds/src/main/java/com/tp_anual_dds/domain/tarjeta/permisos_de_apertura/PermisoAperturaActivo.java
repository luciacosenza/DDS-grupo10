package com.tp_anual_dds.domain.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class PermisoAperturaActivo extends PermisoApertura {
    public PermisoAperturaActivo() {
        heladeraPermitida = null;
        fechaOtorgamiento = LocalDateTime.now();
    }

    @Override
    public HeladeraActiva getHeladeraPermitida() {
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
    public Boolean esHeladeraPermitida(HeladeraActiva heladera) {
        return heladera == heladeraPermitida;
    }

    @Override
    public void setHeladeraPermitida(HeladeraActiva vHeladera) {
        heladeraPermitida = vHeladera;
    }

    @Override
    public void resetHeladeraPermitida() {
        heladeraPermitida = null;
    }
}
