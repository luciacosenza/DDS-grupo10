package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.Telefono;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.TelefonoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log
public class TelefonoService extends MedioDeContactoService {

    private final TelefonoRepository telefonoRepository;

    private final I18nService i18nService;

    public TelefonoService(TelefonoRepository vTelefonoRepository, I18nService vI18nService) {
        super();
        telefonoRepository = vTelefonoRepository;

        i18nService = vI18nService;
    }

    @Override
    public Telefono obtenerMedioDeContacto(Long telefonoId) {
        return telefonoRepository.findById(telefonoId).orElseThrow(() -> new EntityNotFoundException(i18nService.getMessage("obtenerEntidad_exception")));
    }

    @Override
    public Telefono guardarMedioDeContacto(MedioDeContacto telefono) {
        return telefonoRepository.save((Telefono) telefono);
    }

    @Override
    @Async
    public void contactar(Long telefonoId, String asunto, String cuerpo) {} // TODO: Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
