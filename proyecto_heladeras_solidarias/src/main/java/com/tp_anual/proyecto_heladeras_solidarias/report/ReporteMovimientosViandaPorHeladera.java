package com.tp_anual.proyecto_heladeras_solidarias.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

@Getter
public class ReporteMovimientosViandaPorHeladera extends Reporte{
    private final LinkedHashMap<HeladeraActiva, Pair<Integer, Integer>> hashMap = new LinkedHashMap<>();
    
    @Override
    public void programarReporte() {
        
        Runnable reportar;
        reportar = () -> {
            // Limpio el HashMap, para reiniciar el Reporte
            hashMap.clear();
            
            ArrayList<HeladeraActiva> heladeras = Sistema.getHeladeras();
            ArrayList<Colaborador> colaboradores = Sistema.getColaboradores();

            // Obtengo, el total de Contribuciones registradas
            ArrayList<Contribucion> contribuciones = colaboradores.stream()
                .flatMap(colaborador -> colaborador.getContribuciones().stream())
                .collect(Collectors.toCollection(ArrayList::new));
            
            // Obtengo, de esas Contribuciones, las Donaciones de Vianda
            ArrayList<DonacionVianda> donacionesVianda = contribuciones.stream()
                .filter(contribucion -> contribucion instanceof DonacionVianda)
                .map(donacionVianda -> (DonacionVianda) donacionVianda)
                .collect(Collectors.toCollection(ArrayList::new));
            
            // Ovtengo, de esas Contribuciones, las Distribuciones de Viandas
            ArrayList<DistribucionViandas> distribucionesVianda = contribuciones.stream()
                .filter(contribucion -> contribucion instanceof DistribucionViandas)
                .map(distribucionViandas -> (DistribucionViandas) distribucionViandas)
                .collect(Collectors.toCollection(ArrayList::new));

            // Obtengo, por cada Heladera, sus ingresos y egresos de Viandas
            for (HeladeraActiva heladera : heladeras) {
                // Sumo a los ingresos las Viandas introducidas por Donaciones
                Integer ingresos = donacionesVianda.stream()
                    .filter(distribucion -> distribucion.getHeladera() == heladera)
                    .collect(Collectors.toCollection(ArrayList::new))
                    .size(); // Usamos size() porque la cantidad de Viandas donadas siempre es 1 (por Donación)
                
                // Sumo a los ingresos las Viandas introducidas por Distribuciones
                ingresos += distribucionesVianda.stream()
                    .filter(distribucion -> distribucion.getDestino() == heladera)
                    .map(distribucion -> distribucion.getCantidadViandasAMover())
                    .mapToInt(Integer::intValue)
                    .sum();
                
                // Calculo, para los egresos, las Viandas retiradas por Distribuciones
                Integer egresos = distribucionesVianda.stream()
                    .filter(distribucion -> distribucion.getOrigen() == heladera)
                    .map(distribucion -> distribucion.getCantidadViandasAMover())
                    .mapToInt(Integer::intValue)
                    .sum();

                // Armo una Dupla (Pair) con ambos valores
                hashMap.put(heladera, Pair.of(ingresos, egresos));
            }

            System.out.println("REPORTE - MOVIMIENTOS DE VIANDA POR HELADERA\n");
            for (HeladeraActiva heladera : hashMap.keySet()) {
                System.out.println(
                    heladera.getNombre() + ": " +  
                    "Ingresos - " + hashMap.get(heladera).getLeft() + " | " + 
                    "Egresos - " + hashMap.get(heladera).getRight());
            }
        };

        // Programa la tarea para que se ejecute una vez por semana
        scheduler.scheduleAtFixedRate(reportar, 0, 7, TimeUnit.DAYS);
    }
    
    /* Alternativa en la que registramos en el Sistema los Movimientos
    public void programarReporte2() {
        Runnable reportar2 = () -> {
            ArrayList<Heladera> heladeras = Sistema.getHeladera();
            ArrayList<Movimiento> movimientos = Sistema.getMovimientos();
            
            for(Heladera heladera : heladeras) {
                Integer colocacionesPorHeladera = movimientos.stream()
                    .filter(movimiento -> movimiento.getHeladera() == heladera)
                    .filter(movimiento -> movimiento.getTipo() == Movimiento.COLOCACION)
                    .collect(Collectors.toList()).size();

                Integer retirosPorHeladera = movimientos.stream()
                .filter(movimiento -> movimiento.getHeladera() == heladera)
                .filter(movimiento -> movimiento.getTipo() == Movimiento.RETIRO)
                .collect(Collectors.toList()).size();

                System.out.println(String.format("Colocaciones: %d - Retiros: %d", colocacionesPorHeladera, retirosPorHeladera));
            }
        };
        
        scheduler.scheduleAtFixedRate(reportar2, 0, 7, TimeUnit.DAYS);
    }
    */
}