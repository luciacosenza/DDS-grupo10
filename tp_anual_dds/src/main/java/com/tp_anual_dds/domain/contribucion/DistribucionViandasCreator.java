package com.tp_anual_dds.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class DistribucionViandasCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if (args.length != 4 ||
                !(args[0] instanceof HeladeraActiva) ||
                !(args[1] instanceof HeladeraActiva) ||
                !(args[2] instanceof Integer) ||
                !(args[3] instanceof DistribucionViandas.MotivoDistribucion) ||
                (args[0] == args[1])) {
            
            throw new IllegalArgumentException("Argumentos inválidos para realizar una Distribución de Viandas");
        }
        
        return new DistribucionViandas(colaborador, fechaContribucion, (HeladeraActiva) args[0], (HeladeraActiva) args[1], (Integer) args[2], (DistribucionViandas.MotivoDistribucion) args[3]);
    }
}
