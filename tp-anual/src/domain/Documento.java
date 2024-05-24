package domain;

public class Documento {
    String numero;
    TipoDocumento tipo;
    Sexo sexo;
    
    enum TipoDocumento {
        DNI,
        PASAPORTE,
        LICENCIA_CONDUCIR
    }
    
    enum Sexo {
        MASCULINO,
        FEMENINO,
        OTRO
    }
    
    public Documento(String vNumero, TipoDocumento vTipo, Sexo vSexo) {
        numero = vNumero;
        tipo = vTipo;
        sexo = vSexo;
    }
}