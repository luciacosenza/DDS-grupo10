package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Getter
@Setter
public abstract class PermisoApertura {
    protected Heladera heladeraPermitida;
    protected LocalDateTime fechaOtorgamiento;

    public abstract void actualizarFechaOtorgamiento();

    public abstract Boolean esHeladeraPermitida(HeladeraActiva heladera);

    public abstract void resetHeladeraPermitida();
}