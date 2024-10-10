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

import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.GestorDeAperturas;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public abstract class Heladera implements HeladeraObserver {    // Implementa una Interfaz "HeladeraSubject" a nivel conceptual

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;
    
    @NotBlank
    protected String nombre;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ubicacion_id")
    @NotNull
    protected Ubicacion ubicacion;
    
    @NotNull
    @Min(value = 0)
    protected final Integer capacidad;

    @NotNull
    protected final Float tempMin;

    @NotNull
    protected final Float tempMax;

    @OneToMany(mappedBy = "heladera", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @NotNull
    protected final ArrayList<Vianda> viandas;
    
    @NotNull
    protected Float tempActual;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    protected LocalDateTime fechaApertura;

    @NotNull
    protected Boolean estado;

    @Transient
    protected final Integer tiempoPermiso = 3;

    @Transient
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
