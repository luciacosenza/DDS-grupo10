package com.tp_anual_dds.domain.heladera;

import java.util.ArrayList;

import com.tp_anual_dds.domain.incidentes.Alerta;

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
    public ArrayList<Vianda> getViandas() {
        return viandas;
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
    public Vianda retirarVianda() {
        throw new UnsupportedOperationException("Heladera Nula no tiene viandas.");
    }

    @Override
    public void agregarVianda(Vianda vianda) {}
    
    @Override
    public void darDeAlta() {}

    @Override
    public void darDeBaja() {}

    @Override
    public void verificarTempActual() {}

    @Override
    public void setTempActual(Float temperatura) {}

    @Override
    public void reportarAlerta(Alerta.TipoAlerta tipo) {}

    @Override
    public void reportarTemperatura() {}

    @Override
    public void reportarFraude() {}

    @Override
    public void reportarFallaConexion() {}
}
