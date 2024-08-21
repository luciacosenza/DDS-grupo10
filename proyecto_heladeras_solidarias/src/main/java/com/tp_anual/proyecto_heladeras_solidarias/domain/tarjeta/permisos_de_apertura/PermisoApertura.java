package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;

public abstract class PermisoApertura {
    protected Heladera heladeraPermitida;
    protected LocalDateTime fechaOtorgamiento;

    public abstract Heladera getHeladeraPermitida();

    public abstract LocalDateTime getFechaOtorgamiento();

    public abstract void actualizarFechaOtorgamiento();

    public abstract Boolean esHeladeraPermitida(HeladeraActiva heladera);

    public abstract void setHeladeraPermitida(HeladeraActiva heladera);

    public abstract void resetHeladeraPermitida();
}