package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class TarjetaColaboradorCreator implements TarjetaCreator {

    @Override
    public Tarjeta crearTarjeta(Object titular) {
        if (!(titular instanceof ColaboradorHumano)) {
            log.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorCreator.crearTarjeta_err"));
            throw new IllegalArgumentException(I18n.getMessage("tarjeta.TarjetaColaboradorCreator.crearTarjeta_exception"));
        }
        
        return new TarjetaColaboradorActiva((ColaboradorHumano) titular);
    }
}
