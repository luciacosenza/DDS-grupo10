package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public abstract class ExtraccionDeDatos implements ExtraccionDeDatosStrategy {
    protected static final Logger logger = Logger.getLogger(ExtraccionDeDatos.class.getName());
    
    public void confirmarExtraction() {
        logger.log(Level.INFO, I18n.getMessage("migrador.ExtraccionDeDatos.confirmarExtraction_info", getClass().getSimpleName()));
    }

    @Override
    public abstract ArrayList<String[]> extract(String archivo) throws IOException, URISyntaxException ;
}
