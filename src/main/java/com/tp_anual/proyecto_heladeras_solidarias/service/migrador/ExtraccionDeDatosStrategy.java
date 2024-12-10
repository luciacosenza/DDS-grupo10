package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public interface ExtraccionDeDatosStrategy {
    public List<String[]> extract(String archivo) throws IOException, URISyntaxException ;

    public List<String[]> extract(InputStream inputStream) throws IOException, URISyntaxException;

}
