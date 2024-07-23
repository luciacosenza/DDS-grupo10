package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.incidente.Alerta;
import com.tp_anual_dds.domain.suscripcion.Suscripcion;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

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
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public Ubicacion getUbicacion() {
        throw new UnsupportedOperationException("Heladera Nula no tiene ubicación");
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
        throw new UnsupportedOperationException("Heladera Nula no tiene fecha de apertura");
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
    public void notificarColaborador(Suscripcion suscripcion, String asunto, String cuerpo) {}

    @Override
    public void verificarCondiciones() {}

    @Override
    public void agregarVianda(Vianda vianda) {}

    @Override
    public Vianda retirarVianda() {
        throw new UnsupportedOperationException("Heladera Nula no tiene viandas");
    }

    @Override
    public void verificarTempActual() {}

    @Override
    public void setTempActual(Float temperatura) {}

    @Override
    public void desactivar() {}

    @Override
    public void reportarAlerta(Alerta.TipoAlerta tipo) {}

    @Override
    public void reportarTemperatura() {}

    @Override
    public void reportarFraude() {}

    @Override
    public void reportarFallaConexion() {}
}
