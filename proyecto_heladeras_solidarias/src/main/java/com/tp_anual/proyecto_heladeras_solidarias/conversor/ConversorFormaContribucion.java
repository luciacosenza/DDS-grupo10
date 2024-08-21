package com.tp_anual.proyecto_heladeras_solidarias.conversor;

import java.util.HashMap;

import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.ContribucionCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DistribucionViandasCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionDineroCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionViandaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.RegistroDePersonaEnSituacionVulnerableCreator;

// A los conversores les pasamos los strings en minúscula, sólo con caracteres alfabéticos (removiendo numéricos y especiales) y sin espacios
public class ConversorFormaContribucion {
    private static final HashMap<String, ContribucionCreator> conversorFormaContribucion = new HashMap<>();

    static {
        conversorFormaContribucion.put("dinero", new DonacionDineroCreator());
        
        // Esto queda comentado, eran opciones para atajar la falta de normalización (aguardamos respuesta sobre si incluirlos o borrarlos)

        /* 
        conversorFormaContribucion.put("donardinero", new DonacionDineroCreator());
        conversorFormaContribucion.put("donaciondinero", new DonacionDineroCreator());
        conversorFormaContribucion.put("donaciondedinero", new DonacionDineroCreator());
        */


        conversorFormaContribucion.put("donacionviandas", new DonacionViandaCreator());
        
        // Esto queda comentado, eran opciones para atajar la falta de normalización (aguardamos respuesta sobre si incluirlos o borrarlos)

        /* 
        conversorFormaContribucion.put("donarvianda", new DonacionViandaCreator());
        conversorFormaContribucion.put("donarviandas", new DonacionViandaCreator());
        conversorFormaContribucion.put("donacionvianda", new DonacionViandaCreator());
        conversorFormaContribucion.put("donaciondevianda", new DonacionViandaCreator());
        conversorFormaContribucion.put("donaciondeviandas", new DonacionViandaCreator());
        */


        conversorFormaContribucion.put("redistribucionviandas", new DistribucionViandasCreator());

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
        conversorFormaContribucion.put("redistribuciondevianda", new DistribucionViandasCreator());
        conversorFormaContribucion.put("redistribuciondeviandas", new DistribucionViandasCreator());
        */


        conversorFormaContribucion.put("entregatarjetas", new RegistroDePersonaEnSituacionVulnerableCreator());

        // Esto queda comentado, eran opciones para atajar la falta de normalización (aguardamos respuesta sobre si incluirlos o borrarlos)

        /* 
        conversorFormaContribucion.put("entregatarjeta", new RegistroDePersonaEnSituacionVulnerableCreator());
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
        if (creator == null) 
            System.out.println("Forma de contribución no válida");  // TODO Chequear si está bien que lo tire en System.out
        
        return creator;
    }
}
