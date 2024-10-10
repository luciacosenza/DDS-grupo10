package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.PermisoAperturaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class PermisoAperturaService {

    private final PermisoAperturaRepository permisoAperturaRepository;

    public PermisoAperturaService(PermisoAperturaRepository vPermisoAperturaRepository) {
        permisoAperturaRepository = vPermisoAperturaRepository;
    }

    public PermisoApertura obtenerPermisoApertura(Long permisoId){
        return permisoAperturaRepository.findById(permisoId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public PermisoApertura guardarPermisoApertura(PermisoApertura permisoApertura) {
        return permisoAperturaRepository.save(permisoApertura);
    }
}
