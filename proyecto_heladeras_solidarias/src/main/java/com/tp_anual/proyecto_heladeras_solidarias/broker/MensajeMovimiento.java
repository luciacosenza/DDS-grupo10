package com.tp_anual.proyecto_heladeras_solidarias.broker;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.SensorMovimiento;
import lombok.Getter;

@Getter
public class MensajeMovimiento implements Mensaje {
    private final SensorMovimiento sensor;
    private final Boolean movimiento;

    public MensajeMovimiento(SensorMovimiento vSensor, Boolean vMovimiento) {
        sensor = vSensor;
        movimiento = vMovimiento;
    }

    @Override
    public void procesar() {
        sensor.setHayMovimiento(movimiento);
    }
}
