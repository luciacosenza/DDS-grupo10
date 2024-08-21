package com.tp_anual.proyecto_heladeras_solidarias.domain.contacto;

public class Telefono extends MedioDeContacto {
    private final String codPais;
    private final String codArea;
    private final String numero;
    
    public Telefono(String vCodPais, String vCodArea, String vNumero) {
        codPais = vCodPais;
        codArea = vCodArea;
        numero = vNumero;
    }

    public String getCodPais() {
        return codPais;
    }

    public String getCodArea() {
        return codArea;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public void contactar(String asunto, String cuerpo) {} // TODO: Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
