package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.EMail;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.EMailRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@Log
public class EMailService {

    private final EMailRepository eMailRepository;
    private final EMailSender eMailSender;

    public EMailService(EMailRepository vEMailRepository, EMailSender vEMailSender) {
        eMailRepository = vEMailRepository;
        eMailSender = vEMailSender;
    }

    public EMail obtenerEMail(Long eMailId) {
        return eMailRepository.findById(eMailId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public void contactar(Long eMailId, String asunto, String cuerpo) {
        EMail eMail = obtenerEMail(eMailId);

        eMailSender.enviarEMail(eMail.getDireccionCorreo(), asunto, cuerpo);
        log.log(Level.INFO, I18n.getMessage("contacto.EMail.contactar_info", eMail.getDireccionCorreo()));
    }
}
