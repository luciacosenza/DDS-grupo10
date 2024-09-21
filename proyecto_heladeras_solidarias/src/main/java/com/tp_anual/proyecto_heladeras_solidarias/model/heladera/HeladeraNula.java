package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion.CondicionSuscripcion;
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
        log.log(Level.SEVERE, I18n.getMessage("heladera.HeladerNula.getUbicacion_err"));
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
        log.log(Level.SEVERE, I18n.getMessage("heladera.HeladerNula.getFechaApertura_err"));
        throw new UnsupportedOperationException(I18n.getMessage("heladera.HeladeraNula.getFechaApertura_exception"));
    }

    @Override
    public Boolean getEstado() {
        return false;
    }

    @Override
    public GestorDeAperturas getGestorDeAperturas() {
        log.log(Level.SEVERE, I18n.getMessage("heladera.HeladerNula.getGestorDeAperturas_err"));
        throw new UnsupportedOperationException(I18n.getMessage("heladera.HeladeraNula.getGestorDeAperturas_exception"));
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
    public void setGestorDeAperturas(GestorDeAperturas vGestorDeAperturas) {}
    
    @Override
    public void darDeAlta() {}

    @Override
    public void darDeBaja() {}

    @Override
    public Boolean estaVacia() {
        return true;
    }

    @Override
    public Boolean estaLlena() {
        return false;
    }

    @Override
    public Integer viandasActuales() {
        return 0;
    }

    @Override
    protected Boolean verificarCapacidad() {
        return false;
    }

    @Override
    protected void verificarCondiciones() {}

    @Override
    public void agregarVianda(Vianda vianda) {}

    @Override
    public Vianda retirarVianda() {
        log.log(Level.SEVERE, I18n.getMessage("heladera.HeladerNula.retirarVianda_err"));
        throw new UnsupportedOperationException(I18n.getMessage("heladera.HeladeraNula.retirarVianda_exception"));
    }

    @Override
    protected void verificarTempActual() {}

    @Override
    public void setTempActual(Float temperatura) {}

    @Override
    public void marcarComoInactiva() {}

    @Override
    public void reaccionarAnteIncidente() {}

    @Override
    public void reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion condicion, MedioDeContacto medioDeContactoElegido) {}

    @Override
    public void reportarIncidente(Incidente incidente) {}

    @Override
    public void producirAlerta(Alerta.TipoAlerta tipo) {}

    @Override
    public void producirFallaTecnica(Colaborador colaborador, String descripcion, String foto) {}
}
