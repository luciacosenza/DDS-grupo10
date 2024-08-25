package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;

public class DistribucionViandasCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        return new DistribucionViandas(colaborador, fechaContribucion,
            new HeladeraActiva(null, new Ubicacion(null, null, null, null, null), new ArrayList<>(), null, null, null, null),
            new HeladeraActiva(null, new Ubicacion(null, null, null, null, null), new ArrayList<>(), null, null, null, null),
            null, null);
    }
    
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);
        
        if (args.length != 4 ||
            !(args[0] instanceof HeladeraActiva) ||
            !(args[1] instanceof HeladeraActiva) ||
            !(args[2] instanceof Integer) ||
            !(args[3] instanceof DistribucionViandas.MotivoDistribucion) ||
            (args[0] == args[1]))
            
            throw new IllegalArgumentException("Datos inválidos para realizar una distribución de viandas");
        
        return new DistribucionViandas(colaborador, fechaContribucion, (HeladeraActiva) args[0], (HeladeraActiva) args[1], (Integer) args[2], (DistribucionViandas.MotivoDistribucion) args[3]);
    }
}
