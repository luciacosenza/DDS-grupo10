package com.tp_anual.proyecto_heladeras_solidarias.broker;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.sensor.SensorTemperatura;
import lombok.Getter;

@Getter
public class MensajeTemperatura implements Mensaje {
    private final SensorTemperatura sensor;
    private final Float temperatura;

    public MensajeTemperatura(SensorTemperatura vSensor, Float vTemperatura) {
        sensor = vSensor;
        temperatura = vTemperatura;
    }

    @Override
    public void procesar() {
        sensor.setTempActual(temperatura);
    }
    // TODO: Arreglar esto (conectar con Service)
}