package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AccionHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.AccionHeladeraRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

@Service
@Log
public class AccionHeladeraService {

    private final AccionHeladeraRepository accionHeladeraRepository;

    public AccionHeladeraService(AccionHeladeraRepository vAccionHeladeraRepository) {
        accionHeladeraRepository = vAccionHeladeraRepository;
    }

    public AccionHeladera obtenerAccionHeladera(Long accionHeladeraId) {
        return accionHeladeraRepository.findById(accionHeladeraId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public AccionHeladera guardarAccionHeladera(AccionHeladera accionHeladera) {
        return accionHeladeraRepository.save(accionHeladera);
    }
}
