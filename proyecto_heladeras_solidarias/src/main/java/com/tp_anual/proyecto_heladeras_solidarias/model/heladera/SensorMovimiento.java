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

    @Override
    public void notificarHeladera() {
        heladera.producirAlerta(TipoAlerta.FRAUDE);
        log.log(Level.INFO, I18n.getMessage("heladera.SensorMovimiento.notificarHeladera_info", heladera.getNombre()));
    }

    // De esta forma, el Sensor físico le envía cada cierto tiempo si hay movimiento o no (y el lógico se encarga de chequear si la señal fue positiva o negativa)
    public void setHayMovimiento(Boolean vHayMovimiento) {
        hayMovimiento = vHayMovimiento;

        if(hayMovimiento)
            notificarHeladera();
    }
    // Otra forma sería que el Sensor físico mande la señal sólo si hay movimiento, no haciendo falta que chequee el lógico
}