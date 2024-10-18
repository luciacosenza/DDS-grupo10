package com.tp_anual.proyecto_heladeras_solidarias.service.tecnico;

import java.util.List;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Visita;
import com.tp_anual.proyecto_heladeras_solidarias.service.notificador.NotificadorDeIncidentes;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
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

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    public void gestionarVisita() {
        List<Visita> visitas = visitaService.obtenerVisitasNoExitosas();

        while(!visitas.isEmpty()) {
            Visita visita = visitas.removeFirst();

            Incidente incidente = visita.getIncidente();
            notificadorDeIncidentes.notificarIncidente(incidente);

            visitaService.seReviso(visita.getId());
        }
    }
}