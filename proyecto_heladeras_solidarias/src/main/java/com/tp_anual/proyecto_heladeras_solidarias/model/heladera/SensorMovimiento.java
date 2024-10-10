package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta.TipoAlerta;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.validation.constraints.NotNull;

@Entity
@Log
@Getter
@Setter
public class SensorMovimiento extends Sensor {
    
    @NotNull
    private Boolean hayMovimiento;
    
    public SensorMovimiento(HeladeraActiva vHeladera) {
        super(vHeladera);
        hayMovimiento = false;
    }
}