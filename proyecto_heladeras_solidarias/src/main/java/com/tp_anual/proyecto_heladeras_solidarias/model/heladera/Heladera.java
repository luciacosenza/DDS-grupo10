package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

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
public class Heladera implements HeladeraObserver {    // Implementa una Interfaz "HeladeraSubject" a nivel conceptual

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

    protected final ChronoUnit unidadTiempoPermiso = ChronoUnit.HOURS;

    public Heladera(String vNombre, Ubicacion vUbicacion, Integer vCapacidad, Float vTempMin, Float vTempMax, ArrayList<Vianda> vViandas, Float vTempActual, LocalDateTime vFechaApertura, Boolean vEstado) {
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

    public Integer viandasActuales() {return viandas.size();}

    public void marcarComoInactiva() {setEstado(false);}
}
