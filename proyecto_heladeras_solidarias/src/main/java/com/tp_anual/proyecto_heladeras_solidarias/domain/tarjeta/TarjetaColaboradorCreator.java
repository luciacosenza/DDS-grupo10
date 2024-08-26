package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.message_loader.I18n;

public class TarjetaColaboradorCreator implements TarjetaCreator {
    private static final Logger logger = Logger.getLogger(TarjetaColaboradorCreator.class.getName());

    @Override
    public Tarjeta crearTarjeta(Object titular) {
        if (!(titular instanceof ColaboradorHumano)) {
            logger.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorCreator.crearTarjeta_err"));
            throw new IllegalArgumentException(I18n.getMessage("tarjeta.TarjetaColaboradorCreator.crearTarjeta_exception"));
        }
        
        return new TarjetaColaboradorActiva((ColaboradorHumano) titular);
    }
}
