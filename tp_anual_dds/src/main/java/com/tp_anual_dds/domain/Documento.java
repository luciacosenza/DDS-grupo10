package com.tp_anual_dds.domain;

import java.util.HashMap;

public class Documento {
    private TipoDocumento tipo;
    private String numero;
    private Sexo sexo;
    private static final HashMap<String, TipoDocumento> conversorTipoDocumento = new HashMap();
    
    public enum TipoDocumento {
        DNI,
        PASAPORTE,
        LICENCIA_CONDUCIR,
        LIBRETA_CIVICA,
        LIBRETA_ENROLAMIENTO;
    }
    
    public enum Sexo {
        MASCULINO,
        FEMENINO,
        OTRO
    }

    static {
        conversorTipoDocumento.put("dni", TipoDocumento.DNI);
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

        conversorTipoDocumento.put("pasaporte", TipoDocumento.DNI);
        conversorTipoDocumento.put("pp", TipoDocumento.DNI);
        conversorTipoDocumento.put("ppt", TipoDocumento.DNI);
        conversorTipoDocumento.put("pasaportecomun", TipoDocumento.DNI);
        conversorTipoDocumento.put("pasaporteordinario", TipoDocumento.DNI);

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

        conversorTipoDocumento.put("lc",TipoDocumento.LIBRETA_CIVICA);
        conversorTipoDocumento.put("libciv",TipoDocumento.LIBRETA_CIVICA);
        conversorTipoDocumento.put("libcivica",TipoDocumento.LIBRETA_CIVICA);
        conversorTipoDocumento.put("libretaciv",TipoDocumento.LIBRETA_CIVICA);
        conversorTipoDocumento.put("libretacivica",TipoDocumento.LIBRETA_CIVICA);

        conversorTipoDocumento.put("le",TipoDocumento.LIBRETA_ENROLAMIENTO);
        conversorTipoDocumento.put("libenrolamiento",TipoDocumento.LIBRETA_ENROLAMIENTO);
        conversorTipoDocumento.put("libretaenrolamiento",TipoDocumento.LIBRETA_ENROLAMIENTO);
        conversorTipoDocumento.put("libretadeenrolamiento",TipoDocumento.LIBRETA_ENROLAMIENTO);
    }
    
    public Documento(String vNumero, TipoDocumento vTipo, Sexo vSexo) {
        tipo = vTipo;
        numero = vNumero;
        sexo = vSexo;
    }

    public static TipoDocumento convertirStrATipoDocumento(String tipoDocumentoStr) {   // Habria que chequear si es correcto que este metodo se encuentre aca
        return conversorTipoDocumento.get(tipoDocumentoStr);
    }
}