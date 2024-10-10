package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class DonacionDineroCreator implements ContribucionCreator {

    public DonacionDineroCreator() {}

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        DonacionDinero donacionDinero = new DonacionDinero(colaborador, fechaContribucion,null,null);
        donacionDinero.seCompletoYSumoPuntos();

        return donacionDinero;
    }

    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);

        if (args.length != 2 ||
            !(args[0] instanceof Double) ||
            !(args[1] instanceof DonacionDinero.FrecuenciaDePago)) {
            
            log.log(Level.SEVERE, I18n.getMessage("contribucion.DonacionDineroCreator.crearContribucion_err"));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DonacionDineroCreator.crearContribucion_exception"));
        }
        
        return new DonacionDinero(colaborador, fechaContribucion, (Double) args[0], (DonacionDinero.FrecuenciaDePago) args[1]);
    }
}
