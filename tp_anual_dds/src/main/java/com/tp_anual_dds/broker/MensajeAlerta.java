package com.tp_anual_dds.broker;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.incidente.Alerta;

public class MensajeAlerta implements Mensaje {
    private final HeladeraActiva heladera;
    private final Alerta alerta;

    public MensajeAlerta(HeladeraActiva vHeladera, Alerta vAlerta) {
        heladera = vHeladera;
        alerta = vAlerta;
    }

    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    @Override
    public void procesar() {
        // A implementar
    }
}
