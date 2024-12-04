package com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
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
    protected LocalDateTime fecha;  // final

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera")
    protected Heladera heladera;    // final

    protected AccionHeladera() {}

    protected AccionHeladera(LocalDateTime vFecha, Heladera vHeladera) {
        fecha = vFecha;
        heladera = vHeladera;
    }
}
