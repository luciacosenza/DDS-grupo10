package com.tp_anual.proyecto_heladeras_solidarias.service.tecnico;

import java.util.ArrayList;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Visita;
import com.tp_anual.proyecto_heladeras_solidarias.service.notificador.NotificadorDeIncidentes;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class GestorDeVisitas {

    private final VisitaService visitaService;
    private final NotificadorDeIncidentes notificadorDeIncidentes;

    public GestorDeVisitas(VisitaService vVisitaService, NotificadorDeIncidentes vNotificadorDeIncidentes) {
        visitaService = vVisitaService;
        notificadorDeIncidentes = vNotificadorDeIncidentes;
    }

    public void gestionarVisita() {
        // Obtengo la primer Visita registrada
        ArrayList<Visita> visitas = visitaService.obtenerVisitas();
        Visita visita = visitas.removeFirst();
        
        // Chequeo si fue exitosa. Si no lo fue, vuelvo a llamar a un Tecnico para que vaya a ocuparse
        if (!visita.getEstado()) {
            Incidente incidente = visita.getIncidente();
            notificadorDeIncidentes.notificarIncidente(incidente.getId());
        }

        // Tanto si fue exitosa o no, se da de alta en el Sistema
        visita.darDeAlta();
        visitaService.guardarVisita(visita);
    }

    public void agregarVisita(Visita visita) {
        visitaService.guardarVisita(visita);
        gestionarVisita();
    }
}