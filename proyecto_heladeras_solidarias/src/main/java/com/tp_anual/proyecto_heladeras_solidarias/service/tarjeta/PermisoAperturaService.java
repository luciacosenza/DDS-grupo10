package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.PermisoAperturaRepository;
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

    public PermisoApertura obtenerPermisoApertura(Long permisoAperturaId){
        return permisoAperturaRepository.findById(permisoAperturaId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public PermisoApertura guardarPermisoApertura(PermisoApertura permisoApertura) {
        return permisoAperturaRepository.save(permisoApertura);
    }

    public void actualizarFechaOtorgamiento(Long permisoAperturaId){
        PermisoApertura permisoApertura = obtenerPermisoApertura(permisoAperturaId);
        permisoApertura.actualizarFechaOtorgamiento();
        guardarPermisoApertura(permisoApertura);
    }

    public Boolean esHeladeraPermitida(Long permisoAperturaId, Heladera heladera) {
        PermisoApertura permisoApertura = obtenerPermisoApertura(permisoAperturaId);
        return heladera == permisoApertura.getHeladeraPermitida();
    }



}
