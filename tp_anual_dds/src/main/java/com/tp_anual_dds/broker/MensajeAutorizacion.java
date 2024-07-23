package com.tp_anual_dds.broker;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;

public class MensajeAutorizacion implements Mensaje {
    private final ColaboradorHumano colaborador;
    private final Heladera heladera;

    public MensajeAutorizacion(ColaboradorHumano vColaborador, Heladera vHeladera) {
        colaborador = vColaborador;
        heladera = vHeladera;
    }

    public ColaboradorHumano getColaborador() {
        return colaborador;
    }

    public Heladera getHeladera() {
        return heladera;
    }

    @Override
    public void procesar() {
        // A implementar
    }
}
