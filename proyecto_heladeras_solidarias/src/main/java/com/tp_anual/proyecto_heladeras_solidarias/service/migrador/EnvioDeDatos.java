package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public abstract class EnvioDeDatos implements EnvioDeDatosStrategy {

    public void confirmarSending() {
        log.log(Level.INFO, I18n.getMessage("migrador.EnvioDeDatos.confirmarSending_info"));
    }

    @Override
    public abstract void send(ColaboradorHumano colaborador, String asunto, String cuerpo);
}
