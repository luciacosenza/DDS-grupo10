package com.tp_anual_dds.domain.tarjeta;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;

public class TarjetaColaboradorCreator implements TarjetaCreator {
    @Override
    public Tarjeta crearTarjeta(String codigo, Object titular) {
        if(!(titular instanceof ColaboradorHumano)) {
            throw new IllegalArgumentException("Argumentos inválidos para asignar una Tarjeta de Colaborador");
        }

        // En el futuro, podemos agregar una validacion para el codigo, suponiendo que este sea distinto en su estructura para Colaboradores o P.E.S.V.

        return new TarjetaColaboradorActiva(codigo, (ColaboradorHumano) titular);
    }
}
