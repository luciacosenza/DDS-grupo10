package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.Tarjeta;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaboradorActiva;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class TarjetaColaboradorCreator implements TarjetaCreator {

    private final GeneradorCodigo generadorCodigo;

    public TarjetaColaboradorCreator(GeneradorCodigo vGeneradorCodigo) {
        generadorCodigo = vGeneradorCodigo;
    }

    @Override
    public Tarjeta crearTarjeta(Object titular) {
        if (!(titular instanceof ColaboradorHumano)) {
            log.log(Level.SEVERE, I18n.getMessage("tarjeta.TarjetaColaboradorCreator.crearTarjeta_err"));
            throw new IllegalArgumentException(I18n.getMessage("tarjeta.TarjetaColaboradorCreator.crearTarjeta_exception"));
        }

        String codigo = generadorCodigo.generarCodigo(true);
        return new TarjetaColaboradorActiva(codigo, (ColaboradorHumano) titular);
    }
}
