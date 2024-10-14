package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
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
    private final HeladeraService heladeraService;

    public PermisoAperturaService(PermisoAperturaRepository vPermisoAperturaRepository, HeladeraService vHeladeraService) {
        permisoAperturaRepository = vPermisoAperturaRepository;
        heladeraService = vHeladeraService;
    }

    public PermisoApertura obtenerPermisoApertura(Long permisoAperturaId){
        return permisoAperturaRepository.findById(permisoAperturaId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public PermisoApertura guardarPermisoApertura(PermisoApertura permisoApertura) {
        return permisoAperturaRepository.save(permisoApertura);
    }

    public Boolean esHeladeraPermitida(Long permisoAperturaId, Long heladeraId) {
        PermisoApertura permisoApertura = obtenerPermisoApertura(permisoAperturaId);
        Heladera heladera = heladeraService.obtenerHeladera(heladeraId);

        return heladera == permisoApertura.getHeladeraPermitida();
    }
}
