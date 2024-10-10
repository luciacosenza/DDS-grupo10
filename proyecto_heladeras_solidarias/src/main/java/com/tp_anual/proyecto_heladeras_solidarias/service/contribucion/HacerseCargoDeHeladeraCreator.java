package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.HacerseCargoDeHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class HacerseCargoDeHeladeraCreator implements ContribucionCreator {

    public HacerseCargoDeHeladeraCreator() {}

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        return new HacerseCargoDeHeladera(colaborador, fechaContribucion,
            new HeladeraActiva(null, new Ubicacion(null, null, null, null, null, null),
            null, null, null, null, null));
    }

    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);

        if (args.length != 1 ||
            !(args[0] instanceof HeladeraActiva)) {
            
            log.log(Level.SEVERE, I18n.getMessage("contribucion.HacerseCargoDeHeladeraCreator.crearContribucion_err"));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.HacerseCargoDeHeladeraCreator.crearContribucion_exception"));
        }
        
        return new HacerseCargoDeHeladera(colaborador, fechaContribucion, (HeladeraActiva) args[0]);
    }
}
