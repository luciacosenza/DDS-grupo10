package com.tp_anual.proyecto_heladeras_solidarias.conversor;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.ContribucionCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DistribucionViandasCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionDineroCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionViandaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.RegistroDePersonaEnSituacionVulnerableCreator;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class ConversorFormaContribucion {
    private static final HashMap<String, ContribucionCreator> conversorFormaContribucion = new HashMap<>();

    static {
        conversorFormaContribucion.put("DINERO", new DonacionDineroCreator());
        conversorFormaContribucion.put("DONACION_VIANDAS", new DonacionViandaCreator());
        conversorFormaContribucion.put("REDISTRIBUCION_VIANDAS", new DistribucionViandasCreator());
        conversorFormaContribucion.put("ENTREGA_TARJETAS", new RegistroDePersonaEnSituacionVulnerableCreator());
    }

    public static ContribucionCreator convertirStrAContribucionCreator(String formaContribucionStr) {
        ContribucionCreator creator = conversorFormaContribucion.get(formaContribucionStr);
        
        if (creator == null) {
            log.log(Level.SEVERE, I18n.getMessage("conversor.ConversorFormaContribucion.convertirStrAContribucionCreator_err", formaContribucionStr));
            throw new IllegalArgumentException(I18n.getMessage("conversor.ConversorFormaContribucion.convertirStrAContribucionCreator_exception"));
        }

        return creator;
    }
}
