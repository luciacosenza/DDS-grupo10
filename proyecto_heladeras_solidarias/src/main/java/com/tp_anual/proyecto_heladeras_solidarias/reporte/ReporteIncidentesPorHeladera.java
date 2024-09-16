package com.tp_anual.proyecto_heladeras_solidarias.reporte;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import lombok.Getter;

@Getter
public class ReporteIncidentesPorHeladera extends Reporte {
    private final LinkedHashMap<HeladeraActiva, Integer> hashMap = new LinkedHashMap<>(); // Usamos LinkedHashMap para que persista el orden de insercion de los elementos (por fechaInscripcion)
    
    @Override
    public void programarReporte() {
        
        Runnable reportar = () -> {
            // Limpio el HashMap, para reiniciar el Reporte
            hashMap.clear();

            ArrayList<HeladeraActiva> heladeras = Sistema.getHeladeras();
            ArrayList<Incidente> incidentes = Sistema.getIncidentes();

            // Obtengo, por cada Heladera, su cantidad de Incidentes
            for (HeladeraActiva heladera : heladeras) {
                Integer cantidadIncidentes = incidentes.stream()
                    .filter(incidente -> incidente.getHeladera() == heladera)
                    .collect(Collectors.toCollection(ArrayList::new)).size();
                
                hashMap.put(heladera, cantidadIncidentes);
            }
            
            System.out.println("REPORTE - INCIDENTES POR HELADERA\n");
            for (HeladeraActiva heladera : hashMap.keySet()) {
                System.out.println(
                    heladera.getNombre() + ": " +  hashMap.get(heladera));
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