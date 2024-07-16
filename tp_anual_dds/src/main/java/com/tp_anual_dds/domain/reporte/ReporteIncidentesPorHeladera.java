package com.tp_anual_dds.domain.reporte;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.incidentes.Incidente;
import com.tp_anual_dds.sistema.Sistema;

public class ReporteIncidentesPorHeladera extends Reporte {
    @Override
    public void programarReporte() {
        
        Runnable reportar = () -> {
            ArrayList<Heladera> heladeras = Sistema.getHeladeras();
            ArrayList<Incidente> incidentes = Sistema.getIncidentes();

            for(Heladera heladera : heladeras) {
                Integer cantidadIncidentes = incidentes.stream()
                    .filter(incidente -> incidente.getHeladera() == heladera)
                    .collect(Collectors.toList()).size();
                System.out.println(cantidadIncidentes); // Esto es temporal, para que no tire errores. La logica es *reportar*
            }
        };

        // Programa la tarea para que se ejecute una vez por semana
        scheduler.scheduleAtFixedRate(reportar, 0, 7, TimeUnit.DAYS); // Ejecuta cada semana
    }

    /* Alternativa en la que las Heladeras conocen a sus Incidentes
    public void programarReporte2() {
        
        Runnable reportar2 = () -> {
            ArrayList<Heladera> heladeras = Sistema.getHeladeras();

            for(Heladera heladera : heladeras) {
                Integer cantidadIncidentes = heladera.getIncidentes().size();
                System.out.println(cantidadIncidentes);
            }
        };

        scheduler.scheduleAtFixedRate(reportar2, 0, 7, TimeUnit.DAYS); // Ejecuta cada semana
    } */
} 