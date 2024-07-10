package com.tp_anual_dds.domain.tarjeta;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.heladera.Heladera;

public abstract class PermisoApertura {
    protected ArrayList<Heladera> heladerasPermitidas;
    protected LocalDateTime fechaOtorgamiento;

    public abstract ArrayList<Heladera> getHeladerasPermitidas();

    public abstract LocalDateTime getFechaOtorgamiento();

    public abstract void actualizarFechaOtorgamiento();

    public abstract Boolean esHeladeraPermitida(Heladera heladera);

    public abstract void agregarHeladeraPermitida(Heladera heladera);

    public abstract void resetHeladerasPermitidas();

    public abstract Integer cantidadHeladerasPermitidas();
}
