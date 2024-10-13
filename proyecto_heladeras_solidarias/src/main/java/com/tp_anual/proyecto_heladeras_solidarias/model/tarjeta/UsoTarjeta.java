package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@Log
@Getter
@Setter
public class UsoTarjeta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private final LocalDateTime fechaUso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    private final Heladera heladera;

    public UsoTarjeta(LocalDateTime vFechaUso, Heladera vHeladera) {
        fechaUso = vFechaUso;
        heladera = vHeladera;
    }
}