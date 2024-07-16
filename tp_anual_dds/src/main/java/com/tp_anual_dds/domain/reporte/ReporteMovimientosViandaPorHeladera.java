package com.tp_anual_dds.domain.reporte;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.contribuciones.Contribucion;
import com.tp_anual_dds.domain.contribuciones.DistribucionViandas;
import com.tp_anual_dds.domain.contribuciones.DonacionVianda;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.sistema.Sistema;

public class ReporteMovimientosViandaPorHeladera extends Reporte{
    @Override
    public void programarReporte() {
        
        Runnable reportar;
        reportar = () -> {
            ArrayList<Heladera> heladeras = Sistema.getHeladeras();
            ArrayList<Colaborador> colaboradores = Sistema.getColaboradores();
            ArrayList<Contribucion> contribuciones = (ArrayList<Contribucion>) colaboradores.stream()
                .flatMap(colaborador -> colaborador.getContribuciones().stream())
                .collect(Collectors.toList());
            
            ArrayList<DonacionVianda> donacionesVianda = (ArrayList<DonacionVianda>) contribuciones.stream()
                .filter(contribucion -> contribucion instanceof DonacionVianda)
                .map(donacionVianda -> (DonacionVianda) donacionVianda)
                .collect(Collectors.toList());
            
            ArrayList<DistribucionViandas> distribucionesVianda = (ArrayList<DistribucionViandas>) contribuciones.stream()
                .filter(contribucion -> contribucion instanceof DistribucionViandas)
                .map(distribucionViandas -> (DistribucionViandas) distribucionViandas)
                .collect(Collectors.toList());

            for (Heladera heladera : heladeras) {
                Integer ingresos = donacionesVianda.stream()
                    .filter(distribucion -> distribucion.getHeladera() == heladera)
                    .collect(Collectors.toList())
                    .size(); // Usamos size() porque la cantidad de viandas donadas siempre es 1
                
                ingresos += distribucionesVianda.stream()
                    .filter(distribucion -> distribucion.getDestino() == heladera)
                    .map(distribucion -> distribucion.getCantidadViandasAMover())
                    .mapToInt(Integer::intValue)
                    .sum();
                
                Integer egresos = distribucionesVianda.stream()
                    .filter(distribucion -> distribucion.getOrigen() == heladera)
                    .map(distribucion -> distribucion.getCantidadViandasAMover())
                    .mapToInt(Integer::intValue)
                    .sum();
            
                System.out.println(String.format("Colocaciones: %d - Retiros: %d", ingresos, egresos)); // Esto es temporal, para que no tire errores. La logica es *reportar*
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