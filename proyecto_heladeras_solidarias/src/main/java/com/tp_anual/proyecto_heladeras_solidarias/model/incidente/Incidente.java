package com.tp_anual.proyecto_heladeras_solidarias.model.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    protected LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    @NotNull
    protected HeladeraActiva heladera;

    protected Incidente(LocalDateTime vFecha, HeladeraActiva vHeladera) {
        fecha = vFecha;
        heladera = vHeladera;
    }

    public void darDeAlta() {
        Sistema.agregarIncidente(this);
    }

    public void darDeBaja() {
        Sistema.eliminarIncidente(this);
    }
}
