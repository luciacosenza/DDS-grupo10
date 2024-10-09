package com.tp_anual.proyecto_heladeras_solidarias.service.suscripcion;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class GestorDeSuscripciones {

    private final SuscripcionService suscripcionService;

    public GestorDeSuscripciones(SuscripcionService vSuscripcionService) {
        suscripcionService = vSuscripcionService;
    }

    public void agregarSuscripcion(Suscripcion suscripcion) {
        suscripcionService.guardarSuscripcion(suscripcion);
    }
    
    public void eliminarSuscripcion(Suscripcion suscripcion) {
        suscripcionService.eliminarSuscripcion(suscripcion);
    }

    public ArrayList<Suscripcion> suscripcionesPorHeladera(Long heladeraId) {
        return suscripcionService.obtenerSuscripcionesPorHeladera(heladeraId);
    }
}