package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.WhatsApp;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.WhatsAppRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log
public class WhatsAppService extends MedioDeContactoService {

    private final WhatsAppRepository whatsAppRepository;

    public WhatsAppService(WhatsAppRepository vWhatsAppRepository) {
        super();
        whatsAppRepository = vWhatsAppRepository;
    }

    @Override
    public WhatsApp obtenerMedioDeContacto(Long whatsAppId) {
        return whatsAppRepository.findById(whatsAppId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    @Override
    public WhatsApp guardarMedioDeContacto(MedioDeContacto whatsApp) {
        return whatsAppRepository.save((WhatsApp) whatsApp);
    }

    @Override
    @Async
    public void contactar(Long whatsAppId, String asunto, String cuerpo) {} // TODO: Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
