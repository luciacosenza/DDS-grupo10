package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;

public class EnvioNulo extends EnvioDeDatos {   // Esta clase solo existe para los tests, en los que no queremos mandar un mensaje a los colaboradores migrados

    @Override
    public void send(ColaboradorHumano colaborador, String asunto, String cuerpo) {}
}