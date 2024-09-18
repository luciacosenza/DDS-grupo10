package com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

import jakarta.persistence.*;
import lombok.extern.java.Log;
import lombok.Getter;
import lombok.Setter;

@Entity
@Log
@Getter
@Setter
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tecnico_id")
    private final Tecnico tecnico;

    @OneToOne(fetch = FetchType.EAGER)
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

    public void darDeAlta() {
        Sistema.agregarVisita(this);
    }

    public void darDeBaja() {
        Sistema.eliminarVisita(this);
    }
}
