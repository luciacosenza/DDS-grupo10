package com.tp_anual_dds.domain;

public class Documento {
    private TipoDocumento tipo;
    private String numero;
    private Sexo sexo;
    
    public enum TipoDocumento { // El metodo convertirATipoDocumento() esta incompleto y mal implementado. Es temporal, para que no tire error la llamada desde el migrador
        DNI,
        PASAPORTE,
        LICENCIA_CONDUCIR,
        LIBRETA_CIVICA,
        LIBRETA_ENROLAMIENTO;

        
        public static TipoDocumento convertirATipoDocumento(String tipoDocumentoStr) {
            switch(tipoDocumentoStr.toLowerCase().replaceAll("\\s+", "")) {
            
            case "dni":
                return DNI;
            
            case "pasaporte":
                return PASAPORTE;
            
            case "licenciadeconducir":
                return LICENCIA_CONDUCIR;
            
            case "lc":
                return LIBRETA_CIVICA;
            
            case "le":
                return LIBRETA_ENROLAMIENTO;

            default:
                return null;
            }
        }
    }
    
    public enum Sexo {
        MASCULINO,
        FEMENINO,
        OTRO
    }
    
    public Documento(String vNumero, TipoDocumento vTipo, Sexo vSexo) {
        tipo = vTipo;
        numero = vNumero;
        sexo = vSexo;
    }
}