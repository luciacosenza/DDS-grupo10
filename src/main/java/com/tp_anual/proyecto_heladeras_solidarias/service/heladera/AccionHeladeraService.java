package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AccionHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.AccionHeladeraRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class AccionHeladeraService {

    private final AccionHeladeraRepository accionHeladeraRepository;

    private final I18nService i18nService;

    public AccionHeladeraService(AccionHeladeraRepository vAccionHeladeraRepository, I18nService vI18nService) {
        accionHeladeraRepository = vAccionHeladeraRepository;

        i18nService = vI18nService;
    }

    public AccionHeladera obtenerAccionHeladera(Long accionHeladeraId) {
        return accionHeladeraRepository.findById(accionHeladeraId).orElseThrow(() -> new EntityNotFoundException(i18nService.getMessage("obtenerEntidad_exception")));
    }

    public List<AccionHeladera> obtenerAccionesHeladera() {
        return new ArrayList<>(accionHeladeraRepository.findAll());
    }

    public AccionHeladera guardarAccionHeladera(AccionHeladera accionHeladera) {
        return accionHeladeraRepository.save(accionHeladera);
    }
}
