package com.tp_anual.proyecto_heladeras_solidarias.domain.contacto;

public class EMail extends MedioDeContacto {
    private final String direccionCorreo;

    public EMail(String vDireccionCorreo) {
        direccionCorreo = vDireccionCorreo;
    }

    @Override
    public void contactar(String asunto, String cuerpo) {
        EMailSenderService.enviarEMail(direccionCorreo, asunto, cuerpo);
    }
}
