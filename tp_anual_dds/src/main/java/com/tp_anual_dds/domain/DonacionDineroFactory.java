package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public class DonacionDineroFactory implements ContribucionFactory {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if (args.length != 2 ||
                !(args[0] instanceof Double) ||
                !(args[1] instanceof DonacionDinero.FrecuenciaDePago)) {
            
            throw new IllegalArgumentException("Argumentos inválidos para realizar una Donación de Dinero");
        }
        
        return new DonacionDinero(colaborador, fechaContribucion, (Double) args[0], (DonacionDinero.FrecuenciaDePago) args[1]);
    }
}
