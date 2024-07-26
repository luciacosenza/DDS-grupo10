package com.tp_anual_dds.broker;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class MensajeAperturaColaborador implements Mensaje {
    private final HeladeraActiva heladera;
    private final ColaboradorHumano colaborador;

    public MensajeAperturaColaborador(HeladeraActiva vHeladera, ColaboradorHumano vColaborador) {
        heladera = vHeladera;
        colaborador = vColaborador;
    }

    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public ColaboradorHumano getColaborador() {
        return colaborador;
    }

    @Override
    public void procesar() {
        heladera.getGestorDeAperturas().revisarPermisoAperturaC(colaborador);
    }
}