package com.tp_anual.proyecto_heladeras_solidarias.service.incidente;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.repository.incidente.IncidenteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class IncidenteService {

    private final IncidenteRepository incidenteRepository;

    public IncidenteService(IncidenteRepository vIncidenteRepository) {
        incidenteRepository = vIncidenteRepository;
    }

    public Incidente obtenerIncidente(Long incidenteId) {
        return incidenteRepository.findById(incidenteId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public List<Incidente> obtenerIncidentes() {
        return new ArrayList<>(incidenteRepository.findAll());
    }

    public Incidente guardarIncidente(Incidente incidente) {
        return incidenteRepository.save(incidente);
    }
}
