package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.domain.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;

public abstract class Heladera implements HeladeraObserver {    // Implementa una Interfaz "HeladeraSubject" a nivel conceptual
    protected static final Logger logger = Logger.getLogger(Heladera.class.getName());
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
    
    public abstract void verificarCondiciones();

    public abstract void agregarVianda(Vianda vianda);

    public abstract Vianda retirarVianda();

    public abstract void verificarTempActual();

    @Override
    public abstract void setTempActual(Float temperatura);

    public abstract void marcarComoInactiva();

    public abstract void reaccionarAnteIncidente();

    @Override
    public abstract void producirAlerta(Alerta.TipoAlerta tipo);
    
    public abstract void producirFallaTecnica(Colaborador colaborador, String descripcion, String foto);

    public abstract void reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion condicion, MedioDeContacto medioDeContactoElegido);

    public abstract void reportarIncidente(Incidente incidente);
}
