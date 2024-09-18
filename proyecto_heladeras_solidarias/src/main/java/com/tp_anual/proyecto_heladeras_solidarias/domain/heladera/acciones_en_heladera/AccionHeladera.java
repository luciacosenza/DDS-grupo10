package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_accion")
@Getter
public abstract class AccionHeladera {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    protected final LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    protected final Heladera heladera;

    protected AccionHeladera(LocalDateTime vFecha, Heladera vHeladera) {
        fecha = vFecha;
        heladera = vHeladera;
    }

    public void darDeAlta() {
        Sistema.agregarAccionHeladera(this);
    }

    public void darDeBaja() {
        Sistema.eliminarAccionHeladera(this);
    }
}
