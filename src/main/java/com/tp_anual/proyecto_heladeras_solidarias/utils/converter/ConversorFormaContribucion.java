package com.tp_anual.proyecto_heladeras_solidarias.utils.converter;

import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.ContribucionCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DistribucionViandasCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionDineroCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionViandaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.RegistroDePersonaEnSituacionVulnerableCreator;
import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@Log
public class ConversorFormaContribucion {

    private final DonacionDineroCreator donacionDineroCreator;
    private final DonacionViandaCreator donacionViandaCreator;
    private final DistribucionViandasCreator distribucionViandasCreator;
    private final RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator;

    private final HashMap<String, ContribucionCreator> conversorFormaContribucion = new HashMap<>();

    public ConversorFormaContribucion(DonacionDineroCreator vDonacionDineroCreator, DonacionViandaCreator vDonacionViandaCreator, DistribucionViandasCreator vDistribucionViandasCreator, RegistroDePersonaEnSituacionVulnerableCreator vRegistroDePersonaEnSituacionVulnerableCreator) {
        donacionDineroCreator = vDonacionDineroCreator;
        donacionViandaCreator = vDonacionViandaCreator;
        distribucionViandasCreator = vDistribucionViandasCreator;
        registroDePersonaEnSituacionVulnerableCreator = vRegistroDePersonaEnSituacionVulnerableCreator;

        conversorFormaContribucion.put("DINERO", donacionDineroCreator);
        conversorFormaContribucion.put("DONACION_VIANDAS", donacionViandaCreator);
        conversorFormaContribucion.put("REDISTRIBUCION_VIANDAS", distribucionViandasCreator);
        conversorFormaContribucion.put("ENTREGA_TARJETAS", registroDePersonaEnSituacionVulnerableCreator);
    }

    public ContribucionCreator convertirStrAContribucionCreator(String formaContribucionStr) {
        ContribucionCreator creator = conversorFormaContribucion.get(formaContribucionStr);
        
        if (creator == null) {
            MessageSource messageSource = SpringContext.getBean(MessageSource.class);
            String logMessage = messageSource.getMessage("conversor.ConversorFormaContribucion.convertirStrAContribucionCreator_err", new Object[] {formaContribucionStr}, Locale.getDefault());
            String exceptionMessage = messageSource.getMessage("conversor.ConversorFormaContribucion.convertirStrAContribucionCreator_exception", null, Locale.getDefault());

            log.log(Level.SEVERE, logMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }

        return creator;
    }
}
