package com.tp_anual.proyecto_heladeras_solidarias.domain.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import lombok.Getter;

@Getter
public class Alerta extends Incidente {
    private final TipoAlerta tipo;

    public enum TipoAlerta {
        TEMPERATURA,
        FRAUDE,
        FALLA_CONEXION
    }

    public Alerta(LocalDateTime vFecha, HeladeraActiva vHeladera, TipoAlerta vTipo) {
        fecha = vFecha;
        heladera = vHeladera;
        tipo = vTipo;
    }
}
