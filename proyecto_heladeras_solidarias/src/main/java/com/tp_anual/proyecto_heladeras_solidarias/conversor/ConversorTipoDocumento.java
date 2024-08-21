package com.tp_anual.proyecto_heladeras_solidarias.conversor;

import java.util.HashMap;

import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento.TipoDocumento;

// A los conversores les pasamos los strings en minúscula, sólo con caracteres alfabéticos (removiendo numéricos y especiales) y sin espacios
public class ConversorTipoDocumento {
    private static final HashMap<String, TipoDocumento> conversorTipoDocumento = new HashMap<>();

    static {
        conversorTipoDocumento.put("dni", TipoDocumento.DNI);
        
        // Esto queda comentado, eran opciones para atajar la falta de normalización (aguardamos respuesta sobre si incluirlos o borrarlos)
        
        /* 
        conversorTipoDocumento.put("docnacional", TipoDocumento.DNI);
        conversorTipoDocumento.put("docidentidad", TipoDocumento.DNI);
        conversorTipoDocumento.put("docdeidentidad", TipoDocumento.DNI);
        conversorTipoDocumento.put("docnacionalidentidad", TipoDocumento.DNI);
        conversorTipoDocumento.put("docnacionaldeidentidad", TipoDocumento.DNI);
        conversorTipoDocumento.put("documentonacional", TipoDocumento.DNI);
        conversorTipoDocumento.put("documentoidentidad", TipoDocumento.DNI);
        conversorTipoDocumento.put("documentodeidentidad", TipoDocumento.DNI);
        conversorTipoDocumento.put("documentonacionalidentidad", TipoDocumento.DNI);
        conversorTipoDocumento.put("documentonacionaldeidentidad", TipoDocumento.DNI);
        */


        // Esto queda comentado porque no aparece entre las opciones de Tipo Doc en el enunciado de Migración

        /*
        conversorTipoDocumento.put("pp", TipoDocumento.DNI);
        conversorTipoDocumento.put("ppt", TipoDocumento.DNI);
        conversorTipoDocumento.put("pasaporte", TipoDocumento.DNI);
        conversorTipoDocumento.put("pasaportecomun", TipoDocumento.DNI);
        conversorTipoDocumento.put("pasaporteordinario", TipoDocumento.DNI);
        */


        // Esto queda comentado porque no aparece entre las opciones de Tipo Doc en el enunciado de Migración

        /*
        conversorTipoDocumento.put("licconducir",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licdeconducir",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licconduccion",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licdeconduccion",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licmanejo",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licdemanejo",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licenciaconducir",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licenciadeconducir",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licenciaconduccion",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licenciadeconduccion",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licenciamanejo",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("licenciademanejo",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("regconducir",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("regdeconducir",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("regconduccion",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("regdeconduccion",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("regmanejo",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("regdemanejo",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("registroconducir",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("registrodeconducir",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("registroconduccion",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("registrodeconduccion",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("registromanejo",TipoDocumento.LICENCIA_CONDUCIR);
        conversorTipoDocumento.put("registrodemanejo",TipoDocumento.LICENCIA_CONDUCIR);
        */


        conversorTipoDocumento.put("lc",TipoDocumento.LIBRETA_CIVICA);
        
        // Esto queda comentado, eran opciones para atajar la falta de normalización (aguardamos respuesta sobre si incluirlos o borrarlos)

        /*
        conversorTipoDocumento.put("libciv",TipoDocumento.LIBRETA_CIVICA);
        conversorTipoDocumento.put("libcivica",TipoDocumento.LIBRETA_CIVICA);
        conversorTipoDocumento.put("libretaciv",TipoDocumento.LIBRETA_CIVICA);
        conversorTipoDocumento.put("libretacivica",TipoDocumento.LIBRETA_CIVICA);
        */


        conversorTipoDocumento.put("le",TipoDocumento.LIBRETA_ENROLAMIENTO);
        
        // Esto queda comentado, eran opciones para atajar la falta de normalización (aguardamos respuesta sobre si incluirlos o borrarlos)

        /*
        conversorTipoDocumento.put("libenrolamiento",TipoDocumento.LIBRETA_ENROLAMIENTO);
        conversorTipoDocumento.put("libretaenrolamiento",TipoDocumento.LIBRETA_ENROLAMIENTO);
        conversorTipoDocumento.put("libretadeenrolamiento",TipoDocumento.LIBRETA_ENROLAMIENTO);
        */
    }

    public static TipoDocumento convertirStrATipoDocumento(String tipoDocumentoStr) {
        TipoDocumento tipoDocumento = conversorTipoDocumento.get(tipoDocumentoStr);
        if (tipoDocumento == null) 
            System.out.println("Tipo de documento no válido");  // TODO Chequear si está bien que lo tire en System.out
        
        return tipoDocumento;
    }
}
