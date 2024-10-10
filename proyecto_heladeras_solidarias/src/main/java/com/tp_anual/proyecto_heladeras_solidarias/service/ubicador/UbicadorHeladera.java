package com.tp_anual.proyecto_heladeras_solidarias.service.ubicador;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Comparator;

import com.tp_anual.proyecto_heladeras_solidarias.service.area.AreaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.tuple.Pair;

import com.tp_anual.proyecto_heladeras_solidarias.model.area.Area;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;

import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import org.springframework.stereotype.Service;

@Service
@Log
public class UbicadorHeladera {

    private final HeladeraService heladeraService;

    public UbicadorHeladera(HeladeraService vHeladeraService) {
        heladeraService = vHeladeraService;
    }

    public ArrayList<HeladeraActiva> obtenerHeladerasCercanasA(Long heladeraId, Integer cantidadHeladeras) {
        HeladeraActiva heladeraObjetivo = heladeraService.obtenerHeladera(heladeraId);

        //Obtengo la Ubicación de la Heladera en cuestión
        Pair<Double, Double> ubicacionHeladera = Pair.of(heladeraObjetivo.getUbicacion().getLatitud(), heladeraObjetivo.getUbicacion().getLongitud());
        
        // Obtengo la lista de Heladeras 
        ArrayList<HeladeraActiva> heladeras = Sistema.getHeladeras();
        
        // Ordeno Heladeras por distancia
        return heladeras.stream()
                .filter(heladera -> !heladera.equals(heladeraObjetivo) /* Evito incluir la Heladera misma */ && heladera.getEstado() /* Evito incluir Heladeras inactivas */ )
                .sorted(Comparator.comparingDouble(heladera -> AreaService.distanciaEntre2Puntos(ubicacionHeladera, Pair.of(heladera.getUbicacion().getLatitud(), heladera.getUbicacion().getLongitud()))))
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
        HeladeraActiva heladeraMenosLlena = null;
        Integer menorDiferencia = 0;
        
        for (HeladeraActiva heladera : heladeras) {
            Integer diferencia = heladera.getCapacidad() - heladera.viandasActuales();
            if (diferencia > menorDiferencia) {
                menorDiferencia = diferencia;
                heladeraMenosLlena = heladera;
            }
        }

        return heladeraMenosLlena;
    }
}
