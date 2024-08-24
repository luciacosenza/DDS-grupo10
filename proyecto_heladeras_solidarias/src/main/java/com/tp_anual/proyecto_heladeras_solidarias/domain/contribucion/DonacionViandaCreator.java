package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;

public class DonacionViandaCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        return new DonacionVianda(colaborador, fechaContribucion,
            new Vianda(null, null, null, null, null, null, null),
            new HeladeraActiva(null, new Ubicacion(null, null, null, null, null), new ArrayList<>(), null, null, null, null));
    }
    
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);

        if (args.length != 2 ||
            !(args[0] instanceof Vianda) ||
            !(args[1] instanceof HeladeraActiva))
            
            throw new IllegalArgumentException("Datos inválidos para realizar una donación de vianda");
        
        return new DonacionVianda(colaborador, fechaContribucion, (Vianda) args[0], (HeladeraActiva) args[1]);
    }
}
