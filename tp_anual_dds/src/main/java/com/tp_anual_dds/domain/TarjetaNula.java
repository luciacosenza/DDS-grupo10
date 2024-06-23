package com.tp_anual_dds.domain;

import java.util.ArrayList;

public class TarjetaNula extends Tarjeta {
    
    public TarjetaNula() {
        codigo = "N/A";
        usos = new ArrayList<>();
    }

    @Override
    public Boolean puedeUsar() {
        return false;
    }
}
