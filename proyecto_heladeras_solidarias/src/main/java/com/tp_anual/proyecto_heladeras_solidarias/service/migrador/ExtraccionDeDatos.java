package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public abstract class ExtraccionDeDatos implements ExtraccionDeDatosStrategy {

    public ExtraccionDeDatos() {}

    public void confirmarExtraction() {
        log.log(Level.INFO, I18n.getMessage("migrador.ExtraccionDeDatos.confirmarExtraction_info", getClass().getSimpleName()));
    }

    @Override
    public abstract ArrayList<String[]> extract(String archivo) throws IOException, URISyntaxException ;
}
