package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public interface ExtraccionDeDatosStrategy {
    public ArrayList<String[]> extract(String archivo) throws IOException, URISyntaxException ;
}
