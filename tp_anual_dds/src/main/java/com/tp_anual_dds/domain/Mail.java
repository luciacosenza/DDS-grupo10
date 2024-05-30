package com.tp_anual_dds.domain;

public class Mail extends MedioDeContacto {
    String direccionCorreo;

    public Mail(String vDireccionCorreo) {
        direccionCorreo = vDireccionCorreo;
    }

    @Override
    public void contactar(String asunto, String cuerpo) {
        EmailSenderService.enviarMail(direccionCorreo, asunto, cuerpo);
    }
}
