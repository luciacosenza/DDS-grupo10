package com.tp_anual_dds.reporte;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.incidente.Incidente;
import com.tp_anual_dds.sistema.Sistema;

public class ReporteIncidentesPorHeladera extends Reporte {
    @Override
    public void programarReporte() {
        
        Runnable reportar = () -> {
            ArrayList<HeladeraActiva> heladeras = Sistema.getHeladeras();
            ArrayList<Incidente> incidentes = Sistema.getIncidentes();

            LinkedHashMap<HeladeraActiva, Integer> reporte = new LinkedHashMap<>(); // Usamos LinkedHashMap para que persista el orden de insercion de los elementos

            for(HeladeraActiva heladera : heladeras) {
                Integer cantidadIncidentes = incidentes.stream()
                    .filter(incidente -> incidente.getHeladera() == heladera)
                    .collect(Collectors.toList()).size();
                reporte.put(heladera, cantidadIncidentes);
            }

            /*ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            try {
                writer.writeValue(new File("../../../../resources/reports/reporte_incidentes_por_heladera.json"), reporte);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

             
            // System.out.println("REPORTE - INCIDENTES POR HELADERA\n\n");
            for(HeladeraActiva heladera : reporte.keySet()) {
                System.out.println(heladera.getNombre() + ": " +  reporte.get(heladera));
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