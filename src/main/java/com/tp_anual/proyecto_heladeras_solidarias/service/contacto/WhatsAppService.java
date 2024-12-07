package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
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

    private final I18nService i18nService;

    public WhatsAppService(WhatsAppRepository vWhatsAppRepository, I18nService vI18nService) {
        super();
        whatsAppRepository = vWhatsAppRepository;

        i18nService = vI18nService;
    }

    @Override
    public WhatsApp obtenerMedioDeContacto(Long whatsAppId) {
        return whatsAppRepository.findById(whatsAppId).orElseThrow(() -> new EntityNotFoundException(i18nService.getMessage("obtenerEntidad_exception")));
    }

    @Override
    public WhatsApp guardarMedioDeContacto(MedioDeContacto whatsApp) {
        return whatsAppRepository.save((WhatsApp) whatsApp);
    }

    @Override
    @Async
    public void contactar(Long whatsAppId, String asunto, String cuerpo) {} // TODO: Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
