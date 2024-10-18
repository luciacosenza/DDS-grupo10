package com.tp_anual.proyecto_heladeras_solidarias.service.ubicador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.HeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.area.AreaService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.tuple.Pair;

import org.springframework.stereotype.Service;

@Service
@Log
public class UbicadorHeladera {

    private final HeladeraRepository heladeraRepository;    // UbicadorHeladera usa a HeladeraRepository en vez de usar a HeladeraService (como el resto de services) porque, para lo único que necesita a HeladeraService es para obtener la lista de heladeras (además, es un service que no aplica lógica de negocio, ya que sólo se encarga de obtener y filtrar heladeras)

    public UbicadorHeladera(HeladeraRepository vHeladeraRepository) {
        heladeraRepository = vHeladeraRepository;
    }

    public List<Heladera> obtenerHeladerasCercanasA(Heladera heladeraObjetivo, Integer cantidadHeladeras) {
        // Obtengo la Ubicación de la Heladera en cuestión
        Pair<Double, Double> ubicacionHeladera = Pair.of(heladeraObjetivo.getUbicacion().getLatitud(), heladeraObjetivo.getUbicacion().getLongitud());
        
        // Obtengo la lista de Heladeras 
        List<Heladera> heladeras = new ArrayList<>(heladeraRepository.findAll());  // TODO: Buscar la forma de filtrar las heladeras directo en la base de datos
        
        // Ordeno Heladeras por distancia
        return heladeras.stream()
                .filter(heladera -> !heladera.equals(heladeraObjetivo) /* Evito incluir la Heladera misma */ && heladera.getEstado() /* Evito incluir Heladeras inactivas */ )
                .sorted(Comparator.comparingDouble(heladera -> AreaService.distanciaEntre2Puntos(ubicacionHeladera, Pair.of(heladera.getUbicacion().getLatitud(), heladera.getUbicacion().getLongitud()))))
                .limit(cantidadHeladeras)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Heladera obtenerHeladeraMasLlena(List <Heladera> heladeras) {
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

    public Heladera obtenerHeladeraMenosLlena(List <Heladera> heladeras) {
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
