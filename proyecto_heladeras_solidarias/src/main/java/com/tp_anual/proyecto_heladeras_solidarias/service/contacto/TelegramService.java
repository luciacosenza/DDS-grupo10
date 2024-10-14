package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.Telegram;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.TelegramRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class TelegramService extends MedioDeContactoService {

    private final TelegramRepository telegramRepository;

    public TelegramService(TelegramRepository vTelegramRepository) {
        super();
        telegramRepository = vTelegramRepository;
    }

    @Override
    public Telegram obtenerMedioDeContacto(Long telegramId) {
        return telegramRepository.findById(telegramId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    @Override
    public Telegram guardarMedioDeContacto(MedioDeContacto telegram) {
        return telegramRepository.save((Telegram) telegram);
    }

    @Override
    public void contactar(Long telegramId, String asunto, String cuerpo) {} // TODO: Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
