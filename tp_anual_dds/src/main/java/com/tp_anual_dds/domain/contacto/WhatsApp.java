package com.tp_anual_dds.domain.contacto;

public class WhatsApp extends MedioDeContacto {
    private final String numero;    // TODO: Pensar si tiene que contener un Telefono o repetimos los atributos de Telefono

    public WhatsApp(String vNumero) {
        numero = vNumero;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public void contactar(String asunto, String cuerpo) {} // TODO: Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
