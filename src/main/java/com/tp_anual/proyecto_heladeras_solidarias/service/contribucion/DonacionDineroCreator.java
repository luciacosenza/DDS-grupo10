package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion.DatosInvalidosCrearDonacionDineroException;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class DonacionDineroCreator implements ContribucionCreator {

    private final I18nService i18nService;

    public DonacionDineroCreator(I18nService vI18nService) {
        i18nService = vI18nService;
    }

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        DonacionDinero donacionDinero = new DonacionDinero(colaborador, fechaContribucion,null,null);
        donacionDinero.seCompletoYSumoPuntos(); // Llamo directamente al método de donacionDinero, porque no quiero que se guarde en este momento

        return donacionDinero;
    }

    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) throws DatosInvalidosCrearDonacionDineroException {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);

        if (args.length != 2 ||
            !(args[0] instanceof Double) ||
            !(args[1] instanceof DonacionDinero.FrecuenciaDePago)) {
            
            log.log(Level.SEVERE, i18nService.getMessage("contribucion.DonacionDineroCreator.crearContribucion_err"));
            throw new DatosInvalidosCrearDonacionDineroException();
        }
        
        return new DonacionDinero(colaborador, fechaContribucion, (Double) args[0], (DonacionDinero.FrecuenciaDePago) args[1]);
    }
}
