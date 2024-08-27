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

// A los conversores les pasamos los strings en minúscula, sólo con caracteres alfabéticos (removiendo numéricos y especiales) y sin espacios
public class ConversorFormaContribucion {
    private static final Logger logger = Logger.getLogger(ConversorFormaContribucion.class.getName());
    private static final HashMap<String, ContribucionCreator> conversorFormaContribucion = new HashMap<>();

    static {
        conversorFormaContribucion.put("DINERO", new DonacionDineroCreator());
        
        // Esto queda comentado, eran opciones para atajar la falta de normalización (aguardamos respuesta sobre si incluirlos o borrarlos)

        /* 
        conversorFormaContribucion.put("dinero", new DonacionDineroCreator());
        conversorFormaContribucion.put("donardinero", new DonacionDineroCreator());
        conversorFormaContribucion.put("donaciondinero", new DonacionDineroCreator());
        conversorFormaContribucion.put("donaciondedinero", new DonacionDineroCreator());
        */


        conversorFormaContribucion.put("DONACION_VIANDAS", new DonacionViandaCreator());
        
        // Esto queda comentado, eran opciones para atajar la falta de normalización (aguardamos respuesta sobre si incluirlos o borrarlos)

        /* 
        conversorFormaContribucion.put("donarvianda", new DonacionViandaCreator());
        conversorFormaContribucion.put("donarviandas", new DonacionViandaCreator());
        conversorFormaContribucion.put("donacionvianda", new DonacionViandaCreator());
        conversorFormaContribucion.put("donacionviandas", new DonacionViandaCreator());
        conversorFormaContribucion.put("donaciondevianda", new DonacionViandaCreator());
        conversorFormaContribucion.put("donaciondeviandas", new DonacionViandaCreator());
        */


        conversorFormaContribucion.put("REDISTRIBUCION_VIANDAS", new DistribucionViandasCreator());

        // Esto queda comentado, eran opciones para atajar la falta de normalización (aguardamos respuesta sobre si incluirlos o borrarlos)
            
        /* 
        conversorFormaContribucion.put("distribuirvianda", new DistribucionViandasCreator());
        conversorFormaContribucion.put("distribuirviandas", new DistribucionViandasCreator());
        conversorFormaContribucion.put("redistribuirvianda", new DistribucionViandasCreator());
        conversorFormaContribucion.put("redistribuirviandas", new DistribucionViandasCreator());
        conversorFormaContribucion.put("distribucionvianda", new DistribucionViandasCreator());
        conversorFormaContribucion.put("distribucionviandas", new DistribucionViandasCreator());
        conversorFormaContribucion.put("distribuciondevianda", new DistribucionViandasCreator());
        conversorFormaContribucion.put("distribuciondeviandas", new DistribucionViandasCreator());
        conversorFormaContribucion.put("redistribucionvianda", new DistribucionViandasCreator());
        conversorFormaContribucion.put("redistribucionviandas", new DistribucionViandasCreator());
        conversorFormaContribucion.put("redistribuciondevianda", new DistribucionViandasCreator());
        conversorFormaContribucion.put("redistribuciondeviandas", new DistribucionViandasCreator());
        */


        conversorFormaContribucion.put("ENTREGA_TARJETAS", new RegistroDePersonaEnSituacionVulnerableCreator());

        // Esto queda comentado, eran opciones para atajar la falta de normalización (aguardamos respuesta sobre si incluirlos o borrarlos)

        /* 
        conversorFormaContribucion.put("entregatarjeta", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("entregatarjetas", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("entregartarjeta", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("entregartarjetas", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("registrarpersonavulnerable", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("registrarpersonaensitvulnerable", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("registrarpersonaensituacionvulnerable", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("registropersonavulnerable", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("registropersonaensitvulnerable", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("registropersonaensituacionvulnerable", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("registrodepersonavulnerable", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("registrodepersonaensitvulnerable", new RegistroDePersonaEnSituacionVulnerableCreator());
        conversorFormaContribucion.put("registrodepersonaensituacionvulnerable", new RegistroDePersonaEnSituacionVulnerableCreator());
        */
    }

    public static ContribucionCreator convertirStrAContribucionCreator(String formaContribucionStr) {
        ContribucionCreator creator = conversorFormaContribucion.get(formaContribucionStr);
        
        if (creator == null) {
            logger.log(Level.SEVERE, I18n.getMessage("conversor.ConversorFormaContribucion.convertirStrAContribucionCreator_err", formaContribucionStr));
            throw new IllegalArgumentException(I18n.getMessage("conversor.ConversorFormaContribucion.convertirStrAContribucionCreator_exception"));
        }

        return creator;
    }
}
