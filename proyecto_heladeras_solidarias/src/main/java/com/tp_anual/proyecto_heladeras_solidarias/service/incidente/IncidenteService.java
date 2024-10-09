package com.tp_anual.proyecto_heladeras_solidarias.service.incidente;

import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.repository.incidente.IncidenteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class IncidenteService {

    private final IncidenteRepository incidenteRepository;

    public IncidenteService(IncidenteRepository vIncidenteRepository) {
        incidenteRepository = vIncidenteRepository;
    }

    public Incidente obtenerIncidente(Long incidenteId) {
        return incidenteRepository.findById(incidenteId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public Incidente guardarIncidente(Incidente incidente) {
        return incidenteRepository.save(incidente);
    }
}
