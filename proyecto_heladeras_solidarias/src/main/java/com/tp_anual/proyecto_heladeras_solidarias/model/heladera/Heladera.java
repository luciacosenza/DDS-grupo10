package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
public class Heladera implements HeladeraObserver {    // Implementa una Interfaz "HeladeraSubject" a nivel conceptual

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Setter
    protected String nombre;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ubicacion")
    @Setter
    protected Ubicacion ubicacion;
    
    @Min(value = 0)
    protected Integer capacidad;    // final

    protected Float tempMin;    // final

    protected Float tempMax;    // final

    @OneToMany(mappedBy = "heladera", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    protected ArrayList<Vianda> viandas;    // final

    @Setter
    protected Float tempActual;

    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    protected LocalDateTime fechaApertura;

    @Setter
    protected Boolean estado;

    protected Integer tiempoPermiso = 3;    // final

    protected ChronoUnit unidadTiempoPermiso = ChronoUnit.HOURS;  // final

    public Heladera() {}

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
