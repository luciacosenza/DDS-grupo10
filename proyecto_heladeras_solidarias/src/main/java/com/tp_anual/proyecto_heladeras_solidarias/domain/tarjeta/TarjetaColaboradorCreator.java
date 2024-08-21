package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;

public class TarjetaColaboradorCreator implements TarjetaCreator {
    @Override
    public Tarjeta crearTarjeta(Object titular) {
        if (!(titular instanceof ColaboradorHumano))
            throw new IllegalArgumentException("Datos inválidos para asignar una tarjeta solidaria");

        return new TarjetaColaboradorActiva((ColaboradorHumano) titular);
    }
}
