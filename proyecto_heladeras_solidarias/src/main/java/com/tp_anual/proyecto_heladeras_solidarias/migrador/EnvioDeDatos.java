package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;

public abstract class EnvioDeDatos implements EnvioDeDatosStrategy {
    @Override
    public abstract void send(ColaboradorHumano colaborador, String asunto, String cuerpo);
}
