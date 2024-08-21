package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Alerta.TipoAlerta;

public class SensorMovimiento extends Sensor {
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
    }

    // De esta forma, el Sensor físico le envía cada cierto tiempo si hay movimiento o no (y el lógico se encarga de chequear si la señal fue positiva o negativa)
    public void setHayMovimiento(Boolean vHayMovimiento) {
        hayMovimiento = vHayMovimiento;

        if(hayMovimiento)
            notificarHeladera();
    }
    // Otra forma sería que el Sensor físico mande la señal sólo si hay movimiento, no haciendo falta que chequee el lógico
}
