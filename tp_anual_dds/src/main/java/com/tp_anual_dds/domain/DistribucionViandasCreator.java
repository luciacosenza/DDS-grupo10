package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public class DistribucionViandasCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if (args.length != 4 ||
                !(args[0] instanceof Heladera) ||
                !(args[1] instanceof Heladera) ||
                !(args[2] instanceof Integer) ||
                !(args[3] instanceof DistribucionViandas.MotivoDistribucion) ||
                (args[0] == args[1])) {
            
            throw new IllegalArgumentException("Argumentos inválidos para realizar una Distribución de Viandas");
        }
        
        return new DistribucionViandas(colaborador, fechaContribucion, (Heladera) args[0], (Heladera) args[1], (Integer) args[2], (DistribucionViandas.MotivoDistribucion) args[3]);
    }
}
