package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ContribucionFinder {

    ContribucionRepository contribucionRepository;

    private final I18nService i18nService;

    public ContribucionFinder(ContribucionRepository vContribucionRepository, I18nService vI18nService) {
        contribucionRepository = vContribucionRepository;

        i18nService = vI18nService;
    }

    public Contribucion obtenerContribucion(Long contribucionId) {
        return contribucionRepository.findById(contribucionId).orElseThrow(() -> new EntityNotFoundException(i18nService.getMessage("obtenerEntidad_exception")));
    }
}
