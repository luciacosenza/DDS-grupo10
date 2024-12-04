package com.tp_anual.proyecto_heladeras_solidarias.model.heladera.vianda;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class PosesionViandas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador")
    private ColaboradorHumano colaborador;

    @ManyToMany
    @JoinTable(name = "posesion_viandas_vianda", joinColumns = @JoinColumn(name = "posesion_vianda"), inverseJoinColumns = @JoinColumn(name = "vianda"))
    private List<Vianda> viandas;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha;

    public PosesionViandas(ColaboradorHumano vColaborador, List<Vianda> vViandas, LocalDateTime vFecha) {
        colaborador = vColaborador;
        viandas = vViandas;
        fecha = vFecha;
    }

    public PosesionViandas() {}
}