package com.tp_anual_dds.reporte;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.contribucion.DistribucionViandas;
import com.tp_anual_dds.domain.contribucion.DonacionVianda;
import com.tp_anual_dds.sistema.Sistema;

public class ReporteViandasPorColaborador extends Reporte {
    @Override
    public void programarReporte() {
        Runnable reportar = () -> {
            ArrayList<Colaborador> colaboradores = Sistema.getColaboradores();          

            for (Colaborador colaborador : colaboradores) {
                Integer cantidadViandas = colaborador.getContribuciones().stream()
                    .filter(contribucion -> contribucion instanceof DonacionVianda)
                    .collect(Collectors.toList()).size();
                
                cantidadViandas += colaborador.getContribuciones().stream()
                    .filter(contribucion -> contribucion instanceof DistribucionViandas)
                    .map(distribucionViandas -> (DistribucionViandas) distribucionViandas)
                    .map(distribucion -> distribucion.getCantidadViandasAMover())
                    .mapToInt(Integer::intValue)
                    .sum();
                
                System.out.println(String.format("Colaborador: %o - Cantidad: %d", colaborador, cantidadViandas));  // Esto es temporal, para que no tire errores. La logica es *reportar*
            }
        };
        
        // Programa la tarea para que se ejecute una vez por semana
        scheduler.scheduleAtFixedRate(reportar, 0, 7, TimeUnit.DAYS);
    }
}