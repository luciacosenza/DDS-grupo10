package com.tp_anual_dds.migrador;

import java.util.ArrayList;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;

public interface ExtraccionDeDatosStrategy {
    public ArrayList<ColaboradorHumano> extract(String archivo);
}
