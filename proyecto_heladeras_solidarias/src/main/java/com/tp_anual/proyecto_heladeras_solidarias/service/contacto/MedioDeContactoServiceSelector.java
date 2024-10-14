package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.Telefono;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.Telegram;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.WhatsApp;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log
public class MedioDeContactoServiceSelector {

    private final Map<Class<? extends MedioDeContacto>, MedioDeContactoService> medioDeContactoServiceMap = new HashMap<>();

    public MedioDeContactoServiceSelector(EMailService vEMailService, TelefonoService vTelefonoService, TelegramService vTelegramService, WhatsAppService vWhatsAppService) {
        medioDeContactoServiceMap.put(MedioDeContacto.class, vEMailService);
        medioDeContactoServiceMap.put(Telefono.class, vTelefonoService);
        medioDeContactoServiceMap.put(Telegram.class, vTelegramService);
        medioDeContactoServiceMap.put(WhatsApp.class, vWhatsAppService);
    }

    public MedioDeContactoService obtenerMedioDeContactoService(Class<? extends MedioDeContacto> medioDeContactoClass) {
        return medioDeContactoServiceMap.get(medioDeContactoClass);
    }
}
