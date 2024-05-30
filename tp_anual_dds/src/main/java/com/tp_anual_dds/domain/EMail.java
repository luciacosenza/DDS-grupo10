package com.tp_anual_dds.domain;

public class EMail extends MedioDeContacto {
    String direccionCorreo;

    public EMail(String vDireccionCorreo) {
        direccionCorreo = vDireccionCorreo;
    }

    @Override
    public void contactar(String asunto, String cuerpo) {
        EmailSenderService.enviarEMail(direccionCorreo, asunto, cuerpo);
    }
}
