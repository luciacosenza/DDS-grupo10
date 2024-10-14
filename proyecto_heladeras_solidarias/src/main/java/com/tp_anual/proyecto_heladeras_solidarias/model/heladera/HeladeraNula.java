package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class HeladeraNula extends Heladera {

    public HeladeraNula() {
        super("N/A", null, 0, 0f, 0f, new ArrayList<>(), 0f, null, false);
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public Ubicacion getUbicacion() {
        log.log(Level.SEVERE, I18n.getMessage("heladera.HeladeraNula.getUbicacion_err"));
        throw new UnsupportedOperationException(I18n.getMessage("heladera.HeladeraNula.getUbicacion_exception"));
    }

    @Override
    public Integer getCapacidad() {
        return capacidad;
    }

    @Override
    public Float getTempMin() {
        return tempMin;
    }

    @Override public Float getTempMax() {
        return tempMax;
    }

    @Override
    public ArrayList<Vianda> getViandas() {
        return viandas;
    }

    @Override
    public Float getTempActual() {
        return tempActual;
    }

    @Override
    public LocalDateTime getFechaApertura() {
        log.log(Level.SEVERE, I18n.getMessage("heladera.HeladeraNula.getFechaApertura_err"));
        throw new UnsupportedOperationException(I18n.getMessage("heladera.HeladeraNula.getFechaApertura_exception"));
    }

    @Override
    public Boolean getEstado() {
        return false;
    }

    @Override
    public void setNombre(String vNombre) {}

    @Override
    public void setUbicacion(Ubicacion vUbicacion) {}

    @Override
    public void setFechaApertura(LocalDateTime vFechaApertura) {}

    @Override
    public void setEstado(Boolean vEstado) {}

    @Override
    public Integer viandasActuales() {
        return 0;
    }

    @Override
    public void marcarComoInactiva() {}
}
