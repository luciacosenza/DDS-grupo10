package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.MedioDeContactoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class MedioDeContactoService {

    private final MedioDeContactoRepository medioDeContactoRepository;

    public MedioDeContactoService(MedioDeContactoRepository vMedioDeContactoRepository) {
        medioDeContactoRepository = vMedioDeContactoRepository;
    }

    public MedioDeContacto obtenerMedioDeContacto(Long medioDeContactoId) {
        return medioDeContactoRepository.findById(medioDeContactoId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }
}
