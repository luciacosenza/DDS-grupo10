package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ContribucionFinder {

    ContribucionRepository contribucionRepository;

    public ContribucionFinder(ContribucionRepository vContribucionRepository) {
        contribucionRepository = vContribucionRepository;
    }

    public Contribucion obtenerContribucion(Long contribucionId) {
        return contribucionRepository.findById(contribucionId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }
}
