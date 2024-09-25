package com.tp_anual.proyecto_heladeras_solidarias.model.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Entity 
@Getter
@Setter
public class Alerta extends Incidente {
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private final TipoAlerta tipo;

    public enum TipoAlerta {
        TEMPERATURA,
        FRAUDE,
        FALLA_CONEXION
    }

    public Alerta(LocalDateTime vFecha, HeladeraActiva vHeladera, TipoAlerta vTipo) {
        super(vFecha, vHeladera);
        tipo = vTipo;
    }
}
