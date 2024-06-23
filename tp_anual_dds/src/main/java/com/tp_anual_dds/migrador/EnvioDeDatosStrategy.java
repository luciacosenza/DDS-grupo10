package com.tp_anual_dds.migrador;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;

public interface EnvioDeDatosStrategy {
    public void send(ColaboradorHumano colaborador, String asunto, String cuerpo);
}
