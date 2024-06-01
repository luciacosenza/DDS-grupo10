package com.tp_anual_dds.domain;

import java.util.HashMap;

// A los conversores les pasamos los strings: en minuscula, solo con caracteres alfabeticos (removiendo numericos y especiales) y sin espacios
public class ConversorFormaContribucion {
    private static final HashMap<String, ContribucionFactory> conversorFormaContribucion = new HashMap<>();

    static {
        conversorFormaContribucion.put("dinero", new DonacionDineroFactory());
        
        // Esto queda comentado, eran opciones para atajar la falta de normalizacion (aguardamos respuesta sobre si incluirlos o borrarlos)

        /* 
        conversorFormaContribucion.put("donardinero", new DonacionDineroFactory());
        conversorFormaContribucion.put("donaciondinero", new DonacionDineroFactory());
        conversorFormaContribucion.put("donaciondedinero", new DonacionDineroFactory());
        */


        conversorFormaContribucion.put("donacionviandas", new DonacionViandaFactory());
        
        // Esto queda comentado, eran opciones para atajar la falta de normalizacion (aguardamos respuesta sobre si incluirlos o borrarlos)

        /* 
        conversorFormaContribucion.put("donarvianda", new DonacionViandaFactory());
        conversorFormaContribucion.put("donarviandas", new DonacionViandaFactory());
        conversorFormaContribucion.put("donacionvianda", new DonacionViandaFactory());
        conversorFormaContribucion.put("donaciondevianda", new DonacionViandaFactory());
        conversorFormaContribucion.put("donaciondeviandas", new DonacionViandaFactory());
        */


        conversorFormaContribucion.put("redistribucionviandas", new DistribucionViandasFactory());

        // Esto queda comentado, eran opciones para atajar la falta de normalizacion (aguardamos respuesta sobre si incluirlos o borrarlos)
            
        /* 
        conversorFormaContribucion.put("distribuirvianda", new DistribucionViandasFactory());
        conversorFormaContribucion.put("distribuirviandas", new DistribucionViandasFactory());
        conversorFormaContribucion.put("redistribuirvianda", new DistribucionViandasFactory());
        conversorFormaContribucion.put("redistribuirviandas", new DistribucionViandasFactory());
        conversorFormaContribucion.put("distribucionvianda", new DistribucionViandasFactory());
        conversorFormaContribucion.put("distribucionviandas", new DistribucionViandasFactory());
        conversorFormaContribucion.put("distribuciondevianda", new DistribucionViandasFactory());
        conversorFormaContribucion.put("distribuciondeviandas", new DistribucionViandasFactory());
        conversorFormaContribucion.put("redistribucionvianda", new DistribucionViandasFactory());
        conversorFormaContribucion.put("redistribuciondevianda", new DistribucionViandasFactory());
        conversorFormaContribucion.put("redistribuciondeviandas", new DistribucionViandasFactory());
        */


        conversorFormaContribucion.put("entregatarjetas", new RegistroDePersonaEnSituacionVulnerableFactory());

        // Esto queda comentado, eran opciones para atajar la falta de normalizacion (aguardamos respuesta sobre si incluirlos o borrarlos)

        /* 
        conversorFormaContribucion.put("entregatarjeta", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("entregartarjeta", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("entregartarjetas", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("registrarpersonavulnerable", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("registrarpersonaensitvulnerable", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("registrarpersonaensituacionvulnerable", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("registropersonavulnerable", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("registropersonaensitvulnerable", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("registropersonaensituacionvulnerable", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("registrodepersonavulnerable", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("registrodepersonaensitvulnerable", new RegistroDePersonaEnSituacionVulnerableFactory());
        conversorFormaContribucion.put("registrodepersonaensituacionvulnerable", new RegistroDePersonaEnSituacionVulnerableFactory());
        */
    }

    public static ContribucionFactory convertirStrAContribucionFactory(String formaContribucionStr) {
        ContribucionFactory factory = conversorFormaContribucion.get(formaContribucionStr);
        if (factory == null) {
            System.out.println("Forma de contribución no válida");
        }
        return factory;
    }
}
