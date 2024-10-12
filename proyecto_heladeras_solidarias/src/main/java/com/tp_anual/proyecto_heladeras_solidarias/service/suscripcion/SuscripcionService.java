package com.tp_anual.proyecto_heladeras_solidarias.service.suscripcion;

import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.repository.suscripcion.SuscripcionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Log
public class SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;

    public SuscripcionService(SuscripcionRepository vSuscripcionRepository) {
        suscripcionRepository = vSuscripcionRepository;
    }

    public Suscripcion obtenerSuscripcion(Long suscripcionId) {
        return suscripcionRepository.findById(suscripcionId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ArrayList<Suscripcion> obtenerSuscripciones() {
        return new ArrayList<>(suscripcionRepository.findAll());
    }

    public ArrayList<Suscripcion> obtenerSuscripcionesPorHeladera(Long heladeraId) {
        return new ArrayList<>(suscripcionRepository.findByHeladeraId(heladeraId));
    }

    public Suscripcion guardarSuscripcion(Suscripcion suscripcion) {
        return suscripcionRepository.save(suscripcion);
    }

    public void eliminarSuscripcion(Suscripcion suscripcion) {
        suscripcionRepository.delete(suscripcion);
    }
}
