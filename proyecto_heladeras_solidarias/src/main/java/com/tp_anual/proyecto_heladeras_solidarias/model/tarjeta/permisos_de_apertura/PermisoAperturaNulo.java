package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraNula;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
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
        log.log(Level.SEVERE, I18n.getMessage("tarjeta.permisos_de_apertura.PermisoAperturaNulo.getFechaOtorgamiento_exception"));
        throw new UnsupportedOperationException(I18n.getMessage("tarjeta.permisos_de_apertura.PermisoAperturaNulo.getFechaOtorgamiento_exception"));
    }

    @Override
    public void actualizarFechaOtorgamiento() {}

    @Override
    public Boolean esHeladeraPermitida(HeladeraActiva heladera) {
        return false;
    }

    @Override
    public void setHeladeraPermitida(Heladera heladera) {}

    @Override
    public void setFechaOtorgamiento(LocalDateTime fechaOtorgamiento) {}

    @Override
    public void resetHeladeraPermitida() {}
}
