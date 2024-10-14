package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.EMail;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.EMailRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.MedioDeContactoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@Log
public class EMailService extends MedioDeContactoService {

    private final EMailRepository eMailRepository;
    private final EMailSender eMailSender;

    public EMailService(MedioDeContactoRepository vMedioDeContactoRepository, EMailRepository vEMailRepository, EMailSender vEMailSender) {
        super(vMedioDeContactoRepository);
        eMailRepository = vEMailRepository;
        eMailSender = vEMailSender;
    }

    public EMail obtenerEMail(Long eMailId) {
        return eMailRepository.findById(eMailId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public void contactar(Long medioDeContactoId, String asunto, String cuerpo) {
        EMail eMail = obtenerEMail(medioDeContactoId);

        eMailSender.enviarEMail(eMail.getDireccionCorreo(), asunto, cuerpo);
        log.log(Level.INFO, I18n.getMessage("contacto.EMail.contactar_info", eMail.getDireccionCorreo()));
    }
}
