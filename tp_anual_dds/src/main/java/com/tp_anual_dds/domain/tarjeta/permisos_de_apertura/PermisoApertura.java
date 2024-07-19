package com.tp_anual_dds.domain.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public abstract class PermisoApertura {
    protected HeladeraActiva heladeraPermitida;
    protected LocalDateTime fechaOtorgamiento;

    public abstract HeladeraActiva getHeladeraPermitida();

    public abstract LocalDateTime getFechaOtorgamiento();

    public abstract void actualizarFechaOtorgamiento();

    public abstract Boolean esHeladeraPermitida(HeladeraActiva heladera);

    public abstract void setHeladeraPermitida(HeladeraActiva heladera);

    public abstract void resetHeladeraPermitida();
}