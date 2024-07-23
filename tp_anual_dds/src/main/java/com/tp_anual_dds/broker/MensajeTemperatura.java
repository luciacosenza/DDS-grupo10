package com.tp_anual_dds.broker;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class MensajeTemperatura implements Mensaje {
    private final HeladeraActiva heladera;
    private final Float temperatura;

    public MensajeTemperatura(HeladeraActiva vHeladera, Float vTemperatura) {
        heladera = vHeladera;
        temperatura = vTemperatura;
    }

    public HeladeraActiva getHeladera() {
        return heladera;
    }
    
    public Float getTemperatura() {
        return temperatura;
    }

    @Override
    public void procesar() {
        // A implementar
    }
}
