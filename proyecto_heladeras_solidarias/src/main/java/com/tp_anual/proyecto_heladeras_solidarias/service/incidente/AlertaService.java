package com.tp_anual.proyecto_heladeras_solidarias.service.incidente;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.incidente.AlertaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository vAlertaRepository) {
        alertaRepository = vAlertaRepository;
    }

    public Alerta obtenerAlerta(Long idAlerta) {
        return alertaRepository.findById(idAlerta).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public Alerta guardarAlerta(Alerta alerta) {
        return alertaRepository.save(alerta);
    }
}
