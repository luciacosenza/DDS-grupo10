package com.tp_anual_dds.broker;

import com.tp_anual_dds.domain.heladera.SensorMovimiento;

public class MensajeMovimiento implements Mensaje {
    private final SensorMovimiento sensor;
    private final Boolean movimiento;

    public MensajeMovimiento(SensorMovimiento vSensor, Boolean vMovimiento) {
        sensor = vSensor;
        movimiento = vMovimiento;
    }

    public SensorMovimiento getSensor() {
        return sensor;
    }

    public Boolean getMovimiento() {
        return movimiento;
    }

    @Override
    public void procesar() {
        sensor.setHayMovimiento(movimiento);
    }
}
