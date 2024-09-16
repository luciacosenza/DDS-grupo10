package com.tp_anual.proyecto_heladeras_solidarias.reporte;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import lombok.Getter;

@Getter
public class ReporteViandasPorColaborador extends Reporte {
    private final LinkedHashMap<ColaboradorHumano, Integer> hashMap = new LinkedHashMap<>(); // Usamos LinkedHashMap para que persista el orden de insercion de los elementos (por fechaInscripcion)

    @Override
    public void programarReporte() {
        Runnable reportar = () -> {
            // Limpio el HashMap, para reiniciar el Reporte
            hashMap.clear();
            
            ArrayList<Colaborador> colaboradores = Sistema.getColaboradores();

            // Hago un filtro y me quedo únicamente con los Colaboradores Humanos
            ArrayList<ColaboradorHumano> colaboradoresHumanos = colaboradores.stream()
                .filter(colaborador -> colaborador instanceof ColaboradorHumano)
                .map(colaborador -> (ColaboradorHumano) colaborador)
                .collect(Collectors.toCollection(ArrayList::new));

            // Obtengo, por cada Colaborador, su cantidad de Viandas donadas / distribuidas
            for (ColaboradorHumano colaborador : colaboradoresHumanos) {
                // Sumo a la cantidad de Viandas las que corresponden a Donaciones
                Integer cantidadViandas = colaborador.getContribuciones().stream()
                    .filter(contribucion -> contribucion instanceof DonacionVianda)
                    .collect(Collectors.toCollection(ArrayList::new)).size();

                // Sumo a la cantidad de Viandas las que corresponden a Distribuciones
                cantidadViandas += colaborador.getContribuciones().stream()
                    .filter(contribucion -> contribucion instanceof DistribucionViandas)
                    .map(distribucionViandas -> (DistribucionViandas) distribucionViandas)
                    .map(distribucion -> distribucion.getCantidadViandasAMover())
                    .mapToInt(Integer::intValue)
                    .sum();

                    hashMap.put(colaborador, cantidadViandas);
            }

            System.out.println("REPORTE - VIANDAS POR COLABORADOR\n");
            for (ColaboradorHumano colaborador : hashMap.keySet()) {
                System.out.println(
                    colaborador.getPersona().getNombre() + " " +
                    colaborador.getPersona().getApellido() + ": " +  hashMap.get(colaborador));
            }
        };
        
        // Programa la tarea para que se ejecute una vez por semana
        scheduler.scheduleAtFixedRate(reportar, 0, 7, TimeUnit.DAYS);
    }
}