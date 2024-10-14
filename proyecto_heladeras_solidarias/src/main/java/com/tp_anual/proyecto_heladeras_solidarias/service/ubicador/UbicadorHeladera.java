package com.tp_anual.proyecto_heladeras_solidarias.service.ubicador;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Comparator;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.area.AreaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.tuple.Pair;

import org.springframework.stereotype.Service;

@Service
@Log
public class UbicadorHeladera {

    private final HeladeraService heladeraService;

    public UbicadorHeladera(HeladeraService vHeladeraService) {
        heladeraService = vHeladeraService;
    }

    public ArrayList<Heladera> obtenerHeladerasCercanasA(Heladera heladeraObjetivo, Integer cantidadHeladeras) {
        // Obtengo la Ubicación de la Heladera en cuestión
        Pair<Double, Double> ubicacionHeladera = Pair.of(heladeraObjetivo.getUbicacion().getLatitud(), heladeraObjetivo.getUbicacion().getLongitud());
        
        // Obtengo la lista de Heladeras 
        ArrayList<Heladera> heladeras = heladeraService.obtenerHeladeras();
        
        // Ordeno Heladeras por distancia
        return heladeras.stream()
                .filter(heladera -> !heladera.equals(heladeraObjetivo) /* Evito incluir la Heladera misma */ && heladera.getEstado() /* Evito incluir Heladeras inactivas */ )
                .sorted(Comparator.comparingDouble(heladera -> AreaService.distanciaEntre2Puntos(ubicacionHeladera, Pair.of(heladera.getUbicacion().getLatitud(), heladera.getUbicacion().getLongitud()))))
                .limit(cantidadHeladeras)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Heladera obtenerHeladeraMasLlena(ArrayList <Heladera> heladeras) {
        Heladera heladeraMasLlena = null;
        Integer menorDiferencia = Integer.MAX_VALUE;
        
        for (Heladera heladera : heladeras) {
            Integer diferencia = heladera.getCapacidad() - heladera.viandasActuales();
            if (diferencia < menorDiferencia) {
                menorDiferencia = diferencia;
                heladeraMasLlena = heladera;
            }
        }
        
        return heladeraMasLlena;
    }

    public Heladera obtenerHeladeraMenosLlena(ArrayList <Heladera> heladeras) {
        Heladera heladeraMenosLlena = null;
        Integer menorDiferencia = 0;
        
        for (Heladera heladera : heladeras) {
            Integer diferencia = heladera.getCapacidad() - heladera.viandasActuales();
            if (diferencia > menorDiferencia) {
                menorDiferencia = diferencia;
                heladeraMenosLlena = heladera;
            }
        }

        return heladeraMenosLlena;
    }
}
