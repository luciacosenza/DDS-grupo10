package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.incidente.Alerta;
import com.tp_anual_dds.domain.incidente.Incidente;
import com.tp_anual_dds.domain.suscripcion.Suscripcion;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public abstract class Heladera implements HeladeraObserver, HeladeraSubject {
    protected String nombre;
    protected Ubicacion ubicacion;
    protected ArrayList<Vianda> viandas;
    protected Integer capacidad;
    protected LocalDateTime fechaApertura;
    protected Float tempMin;
    protected Float tempMax;
    protected Float tempActual;
    protected Boolean estado;
    protected GestorDeAperturas gestorDeAperturas;
    
    public abstract String getNombre();

    public abstract Ubicacion getUbicacion();

    public abstract ArrayList<Vianda> getViandas();

    public abstract Integer getCapacidad();

    public abstract LocalDateTime getFechaApertura();

    public abstract Float getTempMin();

    public abstract Float getTempMax();

    public abstract Float getTempActual();

    public abstract Boolean getEstado();

    public abstract GestorDeAperturas getGestorDeAperturas();

    public abstract void setEstado(Boolean nuevoEstado);

    public abstract void darDeAlta() ;

    public abstract void darDeBaja();

    public abstract Boolean estaVacia();

    public abstract Boolean estaLlena();

    public abstract Integer viandasActuales();

    public abstract Boolean verificarCapacidad();

    @Override
    public abstract void notificarColaborador(Suscripcion suscripcion, String asunto, String cuerpo);
    
    public abstract void verificarCondiciones();

    public abstract void agregarVianda(Vianda vianda);

    public abstract Vianda retirarVianda();

    public abstract void verificarTempActual();

    @Override
    public abstract void setTempActual(Float temperatura);

    public abstract void marcarComoInactiva();

    @Override
    public abstract void producirAlerta(Alerta.TipoAlerta tipo);
    
    public abstract void producirFallaTecnica(Colaborador colaborador, String descripcion, String foto);

    public abstract void reportarIncidente(Incidente incidente);
}
