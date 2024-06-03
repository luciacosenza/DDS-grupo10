package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public class DonacionViandaCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if(args.length != 1 || !(args[0] instanceof Vianda)) {
            throw new IllegalArgumentException("Argumentos inválidos para realizar una Donación de Vianda");
        }
        
        return new DonacionVianda(colaborador, fechaContribucion, (Vianda) args[0]);
    }
}
