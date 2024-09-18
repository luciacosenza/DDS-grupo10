package com.tp_anual.proyecto_heladeras_solidarias.domain.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter
@Setter
public class Alerta extends Incidente {
    
    @Enumerated(EnumType.STRING)
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
