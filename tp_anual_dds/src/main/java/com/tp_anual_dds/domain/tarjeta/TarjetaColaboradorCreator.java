package com.tp_anual_dds.domain.tarjeta;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;

public class TarjetaColaboradorCreator implements TarjetaCreator {
    @Override
    public Tarjeta crearTarjeta(Object titular) {
        if(!(titular instanceof ColaboradorHumano)) {
            throw new IllegalArgumentException("Argumentos inválidos para asignar una Tarjeta de Colaborador");
        }

        return new TarjetaColaboradorActiva((ColaboradorHumano) titular);
    }
}
