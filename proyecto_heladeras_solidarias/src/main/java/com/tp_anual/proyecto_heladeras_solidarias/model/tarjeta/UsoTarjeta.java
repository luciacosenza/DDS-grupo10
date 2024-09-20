package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import lombok.Getter;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@Log
@Getter
public class UsoTarjeta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private final LocalDateTime fechaUso;

    @JoinColumn(name = "heladera_id")
    private final HeladeraActiva heladera;

    public UsoTarjeta(LocalDateTime vFechaUso, HeladeraActiva vHeladera) {
        fechaUso = vFechaUso;
        heladera = vHeladera;
    }
}