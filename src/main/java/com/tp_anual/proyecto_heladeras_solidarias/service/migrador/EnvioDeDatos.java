package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import java.util.Locale;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;

@Log
public abstract class EnvioDeDatos implements EnvioDeDatosStrategy {

    public EnvioDeDatos() {}

    public void confirmarSending() {
        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String logMessage = messageSource.getMessage("migrador.EnvioDeDatos.confirmarSending_info", null, Locale.getDefault());

        log.log(Level.INFO, logMessage);
    }

    @Override
    public abstract void send(ColaboradorHumano colaborador, String asunto, String cuerpo);
}
