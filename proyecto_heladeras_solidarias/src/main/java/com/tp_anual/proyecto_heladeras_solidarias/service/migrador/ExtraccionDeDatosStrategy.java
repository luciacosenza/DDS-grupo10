package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface ExtraccionDeDatosStrategy {
    public List<String[]> extract(String archivo) throws IOException, URISyntaxException ;
}
