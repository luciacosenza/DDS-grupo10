package com.tp_anual_dds.migrador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public abstract class ExtraccionDeDatos implements ExtraccionDeDatosStrategy {
    @Override
    public abstract ArrayList<String[]> extract(String archivo) throws IOException, URISyntaxException ;
}
