package com.tp_anual_dds.domain.tarjeta;

import java.util.ArrayList;

public class TarjetaColaboradorNula extends TarjetaColaborador {
    public TarjetaColaboradorNula() {
        codigo = "N/A";
        usos = new ArrayList<>();
    }

    @Override
    public Boolean puedeUsar() {
        return false;
    }
}
