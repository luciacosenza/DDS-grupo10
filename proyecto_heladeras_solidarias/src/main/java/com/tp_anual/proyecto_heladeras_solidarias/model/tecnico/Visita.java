package com.tp_anual.proyecto_heladeras_solidarias.model.tecnico;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;

import jakarta.persistence.*;
import lombok.Setter;
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
    @JoinColumn(name = "tecnico")
    private Tecnico tecnico;    // final

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "incidente")
    private Incidente incidente;    // final

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha;    // final

    private String descripcion; // final

    private String foto;    // final
    
    private Boolean estado; // final

    @Setter
    private Boolean revisada;

    public Visita() {}

    public Visita(Tecnico vTecnico, Incidente vIncidente, LocalDateTime vFecha, String vDescripcion, String vFoto, Boolean vEstado, Boolean vRevisada) {
        tecnico = vTecnico;
        incidente = vIncidente;
        fecha = vFecha;
        descripcion = vDescripcion;
        foto = vFoto;
        estado = vEstado;
        revisada = vRevisada;
    }

    public void seReviso() {
        setRevisada(true);
    }
}
