package com.tp_anual.proyecto_heladeras_solidarias.domain.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    protected HeladeraActiva heladera;

    public void darDeAlta() {
        Sistema.agregarIncidente(this);
    }

    public void darDeBaja() {
        Sistema.eliminarIncidente(this);
    }
}
