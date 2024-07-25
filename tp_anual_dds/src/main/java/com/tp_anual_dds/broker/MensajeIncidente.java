package com.tp_anual_dds.broker;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.incidente.Incidente;
import com.tp_anual_dds.sistema.Sistema;

public class MensajeIncidente implements Mensaje {
    private final HeladeraActiva heladera;
    private final Incidente incidente;

    public MensajeIncidente(HeladeraActiva vHeladera, Incidente vIncidente) {
        heladera = vHeladera;
        incidente = vIncidente;
    }

    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public Incidente getIncidente() {
        return incidente;
    }

    @Override
    public void procesar() {
        Sistema.getNotificadorDeIncidentes().notificarIncidente(this.getIncidente());
    }
}