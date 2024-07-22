package com.tp_anual_dds.migrador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public interface ExtraccionDeDatosStrategy {
    public ArrayList<String[]> extract(String archivo) throws IOException, URISyntaxException ;
}
