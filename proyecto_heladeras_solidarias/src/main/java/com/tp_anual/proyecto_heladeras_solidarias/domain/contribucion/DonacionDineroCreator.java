package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;

public class DonacionDineroCreator implements ContribucionCreator {
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
            !(args[1] instanceof DonacionDinero.FrecuenciaDePago))
            
            throw new IllegalArgumentException("Datos inválidos para realizar una donación de dinero");
        
        return new DonacionDinero(colaborador, fechaContribucion, (Double) args[0], (DonacionDinero.FrecuenciaDePago) args[1]);
    }
}
