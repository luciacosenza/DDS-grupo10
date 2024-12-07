package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;

@Log
public abstract class ExtraccionDeDatos implements ExtraccionDeDatosStrategy {

    public ExtraccionDeDatos() {}

    public void confirmarExtraction() {
        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String logMessage = messageSource.getMessage("migrador.ExtraccionDeDatos.confirmarExtraction_info", null, Locale.getDefault());

        log.log(Level.INFO, logMessage);
    }

    @Override
    public abstract List<String[]> extract(String archivo) throws IOException, URISyntaxException ;
}
