package com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.message_loader.I18n;

public class EstadoExpirada implements EstadoSolicitud {
    private static final Logger logger = Logger.getLogger(EstadoExpirada.class.getName());

    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        logger.log(Level.INFO, I18n.getMessage("estado_de_solicitud.EstadoExpirada.manejar_info", tarjeta.getTitular()));
        tarjeta.setEstadoSolicitud(new EstadoPosible());
    }
}