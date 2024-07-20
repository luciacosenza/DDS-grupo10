package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.incidentes.Alerta;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public abstract class Heladera implements HeladeraObserver {
    protected String nombre;
    protected Ubicacion ubicacion;
    protected ArrayList<Vianda> viandas;
    protected Integer capacidad;
    protected LocalDateTime fechaApertura;
    protected Float tempMin;
    protected Float tempMax;
    protected Float tempActual;
    protected Boolean estado;
    
    public abstract ArrayList<Vianda> getViandas();

    public abstract Integer getCapacidad();

    public abstract Float getTempActual();

    public abstract Boolean getEstado();

    public abstract void setEstado(Boolean nuevoEstado);

    public abstract Vianda retirarVianda();

    public abstract void darDeAlta() ;

    public abstract void darDeBaja();

    public abstract Boolean verificarCapacidad();

    public abstract void agregarVianda(Vianda vianda);

    public abstract void verificarTempActual();

    @Override
    public abstract void setTempActual(Float temperatura);

    public abstract void reportarAlerta(Alerta.TipoAlerta tipo);
    
    public abstract void reportarTemperatura();

    @Override
    public abstract void reportarFraude();

    public abstract void reportarFallaConexion();
}
