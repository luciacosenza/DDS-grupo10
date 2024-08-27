package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public abstract class EnvioDeDatos implements EnvioDeDatosStrategy {
    private static final Logger logger = Logger.getLogger(EnvioDeDatos.class.getName());

    public void confirmarSending() {
        logger.log(Level.INFO, I18n.getMessage("migrador.EnvioDeDatos.confirmarSending_info"));
    }

    @Override
    public abstract void send(ColaboradorHumano colaborador, String asunto, String cuerpo);
}
