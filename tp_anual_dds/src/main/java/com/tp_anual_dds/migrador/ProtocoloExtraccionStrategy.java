package com.tp_anual_dds.migrador;

import com.tp_anual_dds.domain.ColaboradorHumano;

import java.util.ArrayList;

public interface ProtocoloExtraccionStrategy {
    public ArrayList<ColaboradorHumano> extract(String archivo);
}