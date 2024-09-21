package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public abstract class Heladera implements HeladeraObserver {    // Implementa una Interfaz "HeladeraSubject" a nivel conceptual

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;

    protected String nombre;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ubicacion_id")
    protected Ubicacion ubicacion;
    
    protected final Integer capacidad;

    protected final Float tempMin;

    protected final Float tempMax;

    @OneToMany(mappedBy = "heladera", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    protected final ArrayList<Vianda> viandas;
    
    protected Float tempActual;

    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime fechaApertura;

    protected Boolean estado;

    @Transient
    protected final Integer tiempoPermiso = 3;

    @Transient
    protected final TimeUnit unidadTiempoPermiso = TimeUnit.HOURS;

    @Transient
    protected GestorDeAperturas gestorDeAperturas;

    protected Heladera(String vNombre, Ubicacion vUbicacion, Integer vCapacidad, Float vTempMin, Float vTempMax, ArrayList<Vianda> vViandas, Float vTempActual, LocalDateTime vFechaApertura, Boolean vEstado) {
        nombre = vNombre;
        ubicacion = vUbicacion;
        capacidad = vCapacidad;
        tempMin = vTempMin;
        tempMax = vTempMax;
        viandas = vViandas;
        tempActual = vTempActual;
        fechaApertura = vFechaApertura;
        estado = vEstado;
    }

    public abstract void darDeAlta() ;

    public abstract void darDeBaja();

    public abstract Boolean estaVacia();

    public abstract Boolean estaLlena();

    public abstract Integer viandasActuales();

    protected abstract Boolean verificarCapacidad();
    
    protected abstract void verificarCondiciones();

    public abstract void agregarVianda(Vianda vianda);

    public abstract Vianda retirarVianda();

    protected abstract void verificarTempActual();

    @Override
    public abstract void setTempActual(Float temperatura);

    public abstract void marcarComoInactiva();

    public abstract void reaccionarAnteIncidente();

    public abstract void reportarEstadoSegunCondicionSuscripcion(CondicionSuscripcion condicion, MedioDeContacto medioDeContactoElegido);

    public abstract void reportarIncidente(Incidente incidente);

    @Override
    public abstract void producirAlerta(Alerta.TipoAlerta tipo);
    
    public abstract void producirFallaTecnica(Colaborador colaborador, String descripcion, String foto);
}
