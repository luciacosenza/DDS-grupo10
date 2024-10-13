package com.tp_anual.proyecto_heladeras_solidarias.service.incidente;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.FallaTecnica;
import com.tp_anual.proyecto_heladeras_solidarias.repository.incidente.FallaTecnicaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class FallaTecnicaService {

    private final FallaTecnicaRepository fallaTecnicaRepository;


    public FallaTecnicaService(FallaTecnicaRepository vFallaTecnicaRepository) {
        fallaTecnicaRepository = vFallaTecnicaRepository;
    }

    public FallaTecnica obtenerFallaTecnica(Long idFallaTecnica) {
        return fallaTecnicaRepository.findById(idFallaTecnica).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public FallaTecnica guardarFallaTecnica(FallaTecnica fallaTecnica) {
        return fallaTecnicaRepository.save(fallaTecnica);
    }
}
