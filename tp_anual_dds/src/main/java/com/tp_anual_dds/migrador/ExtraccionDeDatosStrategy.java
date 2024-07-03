package com.tp_anual_dds.migrador;

import java.util.ArrayList;

public interface ExtraccionDeDatosStrategy {
    public ArrayList<String[]> extract(String archivo);
}
