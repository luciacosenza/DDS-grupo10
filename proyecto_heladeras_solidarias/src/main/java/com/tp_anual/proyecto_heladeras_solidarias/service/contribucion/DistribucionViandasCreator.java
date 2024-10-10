package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class DistribucionViandasCreator implements ContribucionCreator {

    public DistribucionViandasCreator() {}

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        return new DistribucionViandas(colaborador, fechaContribucion,
            new HeladeraActiva(null, new Ubicacion(null, null, null, null, null, null), new ArrayList<>(), null, null, null, null),
            new HeladeraActiva(null, new Ubicacion(null, null, null, null, null, null), new ArrayList<>(), null, null, null, null),
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
            (args[0] == args[1])) {
            
            log.log(Level.SEVERE, I18n.getMessage("contribucion.DistribucionViandasCreator.crearContribucion_err"));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DistribucionViandasCreator.crearContribucion_exception"));
        }
        
        return new DistribucionViandas(colaborador, fechaContribucion, (HeladeraActiva) args[0], (HeladeraActiva) args[1], (Integer) args[2], (DistribucionViandas.MotivoDistribucion) args[3]);
    }
}
