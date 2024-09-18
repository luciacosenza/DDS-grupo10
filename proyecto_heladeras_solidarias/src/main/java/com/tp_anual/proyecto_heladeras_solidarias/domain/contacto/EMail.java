package com.tp_anual.proyecto_heladeras_solidarias.domain.contacto;

import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class EMail extends MedioDeContacto {
    private final String direccionCorreo;

    public EMail(String vDireccionCorreo) {
        direccionCorreo = vDireccionCorreo;
    }

    @Override
    public void contactar(String asunto, String cuerpo) {
        EMailSenderService.enviarEMail(direccionCorreo, asunto, cuerpo);
        log.log(Level.INFO, I18n.getMessage("contacto.EMail.contactar_info", direccionCorreo));
    }
}
