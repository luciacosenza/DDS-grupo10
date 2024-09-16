package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import lombok.Getter;

@Getter
public class UsoTarjeta {
    private final LocalDateTime fechaUso;
    private final HeladeraActiva heladera;

    public UsoTarjeta(LocalDateTime vFechaUso, HeladeraActiva vHeladera) {
        fechaUso = vFechaUso;
        heladera = vHeladera;
    }
}