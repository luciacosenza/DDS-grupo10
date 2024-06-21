package com.tp_anual_dds.migrador;

import com.tp_anual_dds.domain.ColaboradorHumano;

public abstract class EnvioDeDatos implements EnvioDeDatosStrategy {
    @Override
    public abstract void send(ColaboradorHumano colaborador, String asunto, String cuerpo);
}
