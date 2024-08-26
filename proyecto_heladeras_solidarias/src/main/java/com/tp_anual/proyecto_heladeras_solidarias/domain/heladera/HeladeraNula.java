package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.domain.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.message_loader.I18n;

public class HeladeraNula extends Heladera {
    public HeladeraNula() {
        nombre = "N/A";
        ubicacion = null;
        viandas = new ArrayList<>();
        capacidad = 0;
        fechaApertura = null;
        tempMin = 0f;
        tempMax = 0f;
        tempActual = 0f;
        estado = false;
        gestorDeAperturas = null;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public Ubicacion getUbicacion() {
        logger.log(Level.SEVERE, I18n.getMessage("heladera.HeladerNula.getUbicacion_err"));
        throw new UnsupportedOperationException(I18n.getMessage("heladera.HeladeraNula.getUbicacion_exception"));
    }

    @Override
    public ArrayList<Vianda> getViandas() {
        return viandas;
    }

    @Override
    public Integer getCapacidad() {
        return capacidad;
    }

    @Override
    public LocalDateTime getFechaApertura() {
        logger.log(Level.SEVERE, I18n.getMessage("heladera.HeladerNula.getFechaApertura_err"));
        throw new UnsupportedOperationException(I18n.getMessage("heladera.HeladeraNula.getFechaApertura_exception"));
    }

    @Override
    public Float getTempMin() {
        return tempMin;
    }

    @Override public Float getTempMax() {
        return tempMax;
    }

    @Override
    public Float getTempActual() {
        return tempActual;
    }

    @Override
    public Boolean getEstado() {
        return false;
    }

    @Override
    public GestorDeAperturas getGestorDeAperturas() {
        logger.log(Level.SEVERE, I18n.getMessage("heladera.HeladerNula.getGestorDeAperturas_err"));
        throw new UnsupportedOperationException(I18n.getMessage("heladera.HeladeraNula.getGestorDeAperturas_exception"));
    }

    @Override
    public void setEstado(Boolean nuevoEstado) {}
    
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
    public Boolean verificarCapacidad() {
        return false;
    }

    @Override
    public void verificarCondiciones() {}

    @Override
    public void agregarVianda(Vianda vianda) {}

    @Override
    public Vianda retirarVianda() {
        logger.log(Level.SEVERE, I18n.getMessage("heladera.HeladerNula.retirarVianda_err"));
        throw new UnsupportedOperationException(I18n.getMessage("heladera.HeladeraNula.retirarVianda_exception"));
    }

    @Override
    public void verificarTempActual() {}

    @Override
    public void setTempActual(Float temperatura) {}

    @Override
    public void marcarComoInactiva() {}

    @Override
    public void reaccionarAnteIncidente() {}

    @Override
    public void producirAlerta(Alerta.TipoAlerta tipo) {}

    @Override
    public void producirFallaTecnica(Colaborador colaborador, String descripcion, String foto) {}
    
    @Override
    public void reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion condicion, MedioDeContacto medioDeContactoElegido) {}

    @Override
    public void reportarIncidente(Incidente incidente) {}
}
