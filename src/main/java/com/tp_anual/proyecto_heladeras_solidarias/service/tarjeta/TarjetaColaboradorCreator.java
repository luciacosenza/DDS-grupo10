package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta.DatosInvalidosCrearTarjetaColaboradorException;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.Tarjeta;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class TarjetaColaboradorCreator implements TarjetaCreator {

    private final GeneradorCodigo generadorCodigo;

    private final I18nService i18nService;

    public TarjetaColaboradorCreator(GeneradorCodigo vGeneradorCodigo, I18nService vI18nService) {
        generadorCodigo = vGeneradorCodigo;

        i18nService = vI18nService;
    }

    @Override
    public Tarjeta crearTarjeta(Object titular) throws DatosInvalidosCrearTarjetaColaboradorException {
        if (!(titular instanceof ColaboradorHumano)) {
            log.log(Level.SEVERE, i18nService.getMessage("tarjeta.TarjetaColaboradorCreator.crearTarjeta_err"));
            throw new DatosInvalidosCrearTarjetaColaboradorException();
        }

        String codigo = generadorCodigo.generarCodigo(true);
        return new TarjetaColaborador(codigo, (ColaboradorHumano) titular, new ArrayList<PermisoApertura>());
    }
}
