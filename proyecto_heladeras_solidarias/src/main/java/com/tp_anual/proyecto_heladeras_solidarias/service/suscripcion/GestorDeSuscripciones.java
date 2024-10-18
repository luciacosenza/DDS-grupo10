package com.tp_anual.proyecto_heladeras_solidarias.service.suscripcion;

import java.util.List;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
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

    public List<Suscripcion> suscripcionesPorHeladera(Heladera heladera) {
        return suscripcionService.obtenerSuscripcionesPorHeladera(heladera);
    }
}