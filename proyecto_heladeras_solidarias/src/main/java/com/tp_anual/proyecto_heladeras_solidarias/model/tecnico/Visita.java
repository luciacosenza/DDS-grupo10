package com.tp_anual.proyecto_heladeras_solidarias.model.tecnico;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;

import jakarta.persistence.*;
import lombok.extern.java.Log;
import lombok.Getter;

@Entity
@Log
@Getter
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "tecnico_id")
    private final Tecnico tecnico;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "incidente_id")
    private final Incidente incidente;

    @Temporal(TemporalType.TIMESTAMP)
    private final LocalDateTime fecha;

    private final String descripcion;

    private final String foto;
    
    private final Boolean estado;

    public Visita(Tecnico vTecnico, Incidente vIncidente, LocalDateTime vFecha, String vDescripcion, String vFoto, Boolean vExitosa) {
        tecnico = vTecnico;
        incidente = vIncidente;
        fecha = vFecha;
        descripcion = vDescripcion;
        foto = vFoto;
        estado = vExitosa;
    }
}
