package com.tp_anual.proyecto_heladeras_solidarias.domain.ubicador;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Comparator;
import org.apache.commons.lang3.tuple.Pair;

import com.tp_anual.proyecto_heladeras_solidarias.domain.area.Area;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;

import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

public class UbicadorHeladera {

    public UbicadorHeladera() {}

    public ArrayList<HeladeraActiva> obtenerHeladerasCercanasA(HeladeraActiva heladeraObjetivo, Integer cantidadHeladeras) {
        //Obtengo la Ubicación de la Heladera en cuestión
        Pair<Double, Double> ubicacionHeladera = Pair.of(heladeraObjetivo.getUbicacion().getLatitud(), heladeraObjetivo.getUbicacion().getLongitud());
        
        // Obtengo la lista de Heladeras 
        ArrayList<HeladeraActiva> heladeras = Sistema.getHeladeras();
        
        // Ordeno Heladeras por distancia
        return heladeras.stream()
                .filter(heladera -> !heladera.equals(heladeraObjetivo) /* Evito incluir la Heladera misma */ && heladera.getEstado() /* Evito incluir Heladeras inactivas */ ) 
                .sorted(Comparator.comparingDouble(heladera -> Area.distanciaEntre2Puntos(ubicacionHeladera, Pair.of(heladera.getUbicacion().getLatitud(), heladera.getUbicacion().getLongitud()))))
                .limit(cantidadHeladeras)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public HeladeraActiva obtenerHeladeraMasLlena(ArrayList <HeladeraActiva> heladeras) {
        HeladeraActiva heladeraMasLlena = null;
        Integer menorDiferencia = Integer.MAX_VALUE;
        
        for (HeladeraActiva heladera : heladeras) {
            Integer diferencia = heladera.getCapacidad() - heladera.viandasActuales();
            if (diferencia < menorDiferencia) {
                menorDiferencia = diferencia;
                heladeraMasLlena = heladera;
            }
        }
        
        return heladeraMasLlena;
    }

    public HeladeraActiva obtenerHeladeraMenosLlena(ArrayList <HeladeraActiva> heladeras) {
        HeladeraActiva heladeraMasLlena = null;
        Integer menorDiferencia = 0;
        
        for (HeladeraActiva heladera : heladeras) {
            Integer diferencia = heladera.getCapacidad() - heladera.viandasActuales();
            if (diferencia > menorDiferencia) {
                menorDiferencia = diferencia;
                heladeraMasLlena = heladera;
            }
        }

        return heladeraMasLlena;
    }
}
