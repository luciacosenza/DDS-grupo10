package com.tp_anual.proyecto_heladeras_solidarias.model.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
    protected LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    protected Heladera heladera;

    protected Incidente(LocalDateTime vFecha, Heladera vHeladera) {
        fecha = vFecha;
        heladera = vHeladera;
    }
}
