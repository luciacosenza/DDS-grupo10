package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.message_loader.I18n;

public class DonacionDineroCreator implements ContribucionCreator {
    private static final Logger logger = Logger.getLogger(DonacionDineroCreator.class.getName());

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        return new DonacionDinero(colaborador, fechaContribucion,null,null);
    }

    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);

        if (args.length != 2 ||
            !(args[0] instanceof Double) ||
            !(args[1] instanceof DonacionDinero.FrecuenciaDePago)) {
            
            logger.log(Level.SEVERE, I18n.getMessage("contribucion.DonacionDineroCreator.crearContribucion_err"));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DonacionDineroCreator.crearContribucion_exception"));
        }
        
        return new DonacionDinero(colaborador, fechaContribucion, (Double) args[0], (DonacionDinero.FrecuenciaDePago) args[1]);
    }
}
