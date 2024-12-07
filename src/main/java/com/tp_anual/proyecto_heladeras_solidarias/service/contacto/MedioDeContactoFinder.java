package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.MedioDeContactoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MedioDeContactoFinder {

    private final MedioDeContactoRepository medioDeContactoRepository;

    private final I18nService i18nService;

    public MedioDeContactoFinder(MedioDeContactoRepository vMedioDeContactoRepository, I18nService vI18nService) {
        medioDeContactoRepository = vMedioDeContactoRepository;

        i18nService = vI18nService;

    }

    public MedioDeContacto obtenerMedioDeContacto(Long medioDeContactoId) {
        return medioDeContactoRepository.findById(medioDeContactoId).orElseThrow(() -> new EntityNotFoundException(i18nService.getMessage("obtenerEntidad_exception")));
    }
}
