package com.tp_anual_dds.domain.contacto;

public class Telegram extends MedioDeContacto{
    private final String numero;
    
    public Telegram(String vNumero) {
        numero = vNumero;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public void contactar(String asunto, String cuerpo) {} // TODO Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
