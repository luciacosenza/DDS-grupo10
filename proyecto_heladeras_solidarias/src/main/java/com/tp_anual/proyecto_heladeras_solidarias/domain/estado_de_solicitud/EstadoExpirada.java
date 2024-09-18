package com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class EstadoExpirada implements EstadoSolicitud {

    @Override
    public void manejar(TarjetaColaborador tarjeta) {
        log.log(Level.INFO, I18n.getMessage("estado_de_solicitud.EstadoExpirada.manejar_info", tarjeta.getTitular().getPersona().getNombre(2)));
        tarjeta.setEstadoSolicitud(new EstadoPosible());
    }
}