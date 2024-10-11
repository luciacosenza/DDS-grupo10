package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;

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
    
    @Min(value = 0)
    protected final Integer capacidad;

    protected final Float tempMin;

    protected final Float tempMax;

    @OneToMany(mappedBy = "heladera", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    protected final ArrayList<Vianda> viandas;
    
    protected Float tempActual;

    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime fechaApertura;

    protected Boolean estado;

    protected final Integer tiempoPermiso = 3;

    protected final TimeUnit unidadTiempoPermiso = TimeUnit.HOURS;

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

    public abstract Integer viandasActuales();

    public abstract void marcarComoInactiva();
}
