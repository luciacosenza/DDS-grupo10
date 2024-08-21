package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;

public interface EnvioDeDatosStrategy {
    public void send(ColaboradorHumano colaborador, String asunto, String cuerpo);
}
