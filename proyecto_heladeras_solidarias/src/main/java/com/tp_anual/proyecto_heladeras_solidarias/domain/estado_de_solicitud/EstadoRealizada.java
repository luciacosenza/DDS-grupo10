package com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class EstadoRealizada implements EstadoSolicitud {
    private static final Logger logger = Logger.getLogger(EstadoRealizada.class.getName());

    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        logger.log(Level.SEVERE, I18n.getMessage("estado_de_solicitud.EstadoRealizada.manejar_err", tarjeta.getTitular()));
        throw new UnsupportedOperationException(I18n.getMessage("estado_de_solicitud.EstadoRealizada.manejar_exception"));
    }
}
