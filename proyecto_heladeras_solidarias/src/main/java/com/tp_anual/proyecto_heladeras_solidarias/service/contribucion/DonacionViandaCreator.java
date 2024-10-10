package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class DonacionViandaCreator implements ContribucionCreator {

    public DonacionViandaCreator() {}

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        return new DonacionVianda(colaborador, fechaContribucion,
            new Vianda(null, null, null, null, null, null, null),
            new HeladeraActiva(null, new Ubicacion(null, null, null, null, null, null), new ArrayList<>(), null, null, null, null));
    }
    
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);

        if (args.length != 2 ||
            !(args[0] instanceof Vianda) ||
            !(args[1] instanceof HeladeraActiva)) {
            
            log.log(Level.SEVERE, I18n.getMessage("contribucion.DonacionViandaCreator.crearContribucion_err"));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DonacionViandaCreator.crearContribucion_exception"));
        }
        
        return new DonacionVianda(colaborador, fechaContribucion, (Vianda) args[0], (HeladeraActiva) args[1]);
    }
}
