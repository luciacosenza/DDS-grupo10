package com.tp_anual.proyecto_heladeras_solidarias.model.estado_de_solicitud;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class EstadoRealizada implements EstadoSolicitud {

    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        log.log(Level.SEVERE, I18n.getMessage("estado_de_solicitud.EstadoRealizada.manejar_err", tarjeta.getTitular()));
        throw new UnsupportedOperationException(I18n.getMessage("estado_de_solicitud.EstadoRealizada.manejar_exception"));
    }
}