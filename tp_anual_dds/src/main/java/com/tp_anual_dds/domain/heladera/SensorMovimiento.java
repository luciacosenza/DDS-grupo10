package com.tp_anual_dds.domain.heladera;

import com.tp_anual_dds.domain.incidente.Alerta.TipoAlerta;

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

    public void setHayMovimiento(Boolean vHayMovimiento) {
        hayMovimiento = vHayMovimiento;

        if(hayMovimiento)
            notificarHeladera();
    }
}
