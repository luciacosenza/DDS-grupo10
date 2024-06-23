package com.tp_anual_dds.domain.tarjeta;

import java.util.ArrayList;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;

public class TarjetaColaborador extends Tarjeta {
    private ColaboradorHumano titular;

    public TarjetaColaborador(String vCodigo, ColaboradorHumano vTitular) {
        codigo = vCodigo;
        usos = new ArrayList<>();
        titular = vTitular;
    }

    public ColaboradorHumano getTitular() {
        return titular;
    }

    @Override
    public Boolean puedeUsar() {
        return true; // Se puede definir alguna lógica especifica si es necesario.
    }

}
