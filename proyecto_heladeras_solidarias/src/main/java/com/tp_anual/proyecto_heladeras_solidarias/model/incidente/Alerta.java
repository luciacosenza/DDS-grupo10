package com.tp_anual.proyecto_heladeras_solidarias.model.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;

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

    public Alerta(LocalDateTime vFecha, Heladera vHeladera, TipoAlerta vTipo) {
        super(vFecha, vHeladera);
        tipo = vTipo;
    }
}
