package com.tp_anual_dds.domain.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.HeladeraNula;

public class PermisoAperturaNulo extends PermisoApertura {
    public PermisoAperturaNulo() {
        heladeraPermitida = new HeladeraNula();
        fechaOtorgamiento = null;
    }

    @Override
    public HeladeraNula getHeladeraPermitida() {
        return (HeladeraNula) heladeraPermitida;
    }

    @Override
    public LocalDateTime getFechaOtorgamiento() {
        throw new UnsupportedOperationException("Permiso Nulo no tiene fecha de otorgamiento");
    }

    @Override
    public void actualizarFechaOtorgamiento() {}

    @Override
    public Boolean esHeladeraPermitida(HeladeraActiva heladera) {
        return false;
    }

    @Override
    public void setHeladeraPermitida(HeladeraActiva heladera) {}

    @Override
    public void resetHeladeraPermitida() {}
}
