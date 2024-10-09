package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;

public interface EnvioDeDatosStrategy {
    public void send(ColaboradorHumano colaborador, String asunto, String cuerpo);
}
