package com.tp_anual_dds.domain.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.HeladeraNula;

public class PermisoAperturaActivo extends PermisoApertura {
    public PermisoAperturaActivo() {
        heladeraPermitida = new HeladeraNula();
        fechaOtorgamiento = null;
    }

    @Override
    public HeladeraActiva getHeladeraPermitida() {
        return (HeladeraActiva) heladeraPermitida;
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

    // Este método es llamado cuando se revocan los Permisos de Apertura sobre una Heladera
    @Override
    public void resetHeladeraPermitida() {
        heladeraPermitida = null;
    }
}
