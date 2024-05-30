package com.tp_anual_dds.domain;

import java.util.HashMap;

// Esto existe suponiendo que hay que normalizar la entrada del campo "Forma de Colaboracion" del csv (si ya estan normalizados, no haria falta)
// A los conversores les pasamos los strings: en minuscula, solo con caracteres alfabeticos (removiendo numericos y especiales) y sin espacios
public class ConversorFormaContribucion {
    private static final HashMap<String, ContribucionFactory> conversorFormaContribucion = new HashMap<>();

    static {
        conversorFormaContribucion.put("dinero", new DonacionDineroFactory());
        conversorFormaContribucion.put("donacionviandas", new DonacionViandaFactory());
        conversorFormaContribucion.put("redistribucionviandas", new DistribucionViandasFactory());
        conversorFormaContribucion.put("entregatarjetas", new RegistroDePersonaEnSituacionVulnerableFactory());
    }

    public static ContribucionFactory convertirStrAContribucionFactory(String formaContribucionStr) {
        return conversorFormaContribucion.get(formaContribucionStr);
    }
}
