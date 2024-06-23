package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;

public class DonacionDineroCreator implements ContribucionCreator {
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
