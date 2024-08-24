package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;

public class HacerseCargoDeHeladeraCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador,LocalDateTime fechaContribucion) {
        return new HacerseCargoDeHeladera(colaborador, fechaContribucion,
            new HeladeraActiva(null, new Ubicacion(null, null, null, null, null),
            null, null, null, null, null));
    }

    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);

        if (args.length != 1 ||
            !(args[0] instanceof HeladeraActiva))
            
            throw new IllegalArgumentException("Datos inválidos para hacerse cargo de una heladera");
        
        return new HacerseCargoDeHeladera(colaborador, fechaContribucion, (HeladeraActiva) args[0]);
    }
}
