package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Alerta.TipoAlerta;
import com.tp_anual.proyecto_heladeras_solidarias.message_loader.I18n;

public class SensorMovimiento extends Sensor {
    private static final Logger logger = Logger.getLogger(SensorMovimiento.class.getName());
    private Boolean hayMovimiento;
    
    public SensorMovimiento(HeladeraActiva vHeladera) {
        heladera = vHeladera;
        hayMovimiento = false;
    }
    
    @Override
    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public Boolean getMovimiento() {
        return hayMovimiento;
    }

    @Override
    public void notificarHeladera() {
        heladera.producirAlerta(TipoAlerta.FRAUDE);
        logger.log(Level.INFO, I18n.getMessage("heladera.SensorMovimiento.notificarHeladera_info", heladera.getNombre()));
    }

    // De esta forma, el Sensor físico le envía cada cierto tiempo si hay movimiento o no (y el lógico se encarga de chequear si la señal fue positiva o negativa)
    public void setHayMovimiento(Boolean vHayMovimiento) {
        hayMovimiento = vHayMovimiento;

        if(hayMovimiento)
            notificarHeladera();
    }
    // Otra forma sería que el Sensor físico mande la señal sólo si hay movimiento, no haciendo falta que chequee el lógico
}