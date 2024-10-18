package com.tp_anual.proyecto_heladeras_solidarias.service.ubicador;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.tp_anual.proyecto_heladeras_solidarias.service.area.AreaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tecnico.TecnicoService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.tuple.Pair;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.stereotype.Service;

@Service
@Log
public class UbicadorTecnico {
    private final AreaService areaService;
    private final TecnicoService tecnicoService;

    public UbicadorTecnico(AreaService vAreaService, TecnicoService vTecnicoService) {
        areaService = vAreaService;
        tecnicoService = vTecnicoService;
    }

    public Tecnico obtenerTecnicoCercanoA(Incidente incidente) {
        // Obtengo la Ubicación de la Heladera en cuestión
        Heladera heladera = incidente.getHeladera();
        Pair<Double, Double> ubicacionHeladera = Pair.of(heladera.getUbicacion().getLatitud(), heladera.getUbicacion().getLongitud());
        
        // Obtengo la lista de Técnicos que tengan a la Heladera en su área de cobertura
        List<Tecnico> tecnicos = tecnicoService.obtenerTecnicos().stream()
            .filter(tecnico -> areaService.estaDentro(tecnico.getAreaDeCobertura().getId(), ubicacionHeladera.getLeft(), ubicacionHeladera.getRight()))
            .collect(Collectors.toCollection(ArrayList::new));
        
        // Obtengo las Ubicaciones de los Técnicos
        List<Pair<Double, Double>> puntosMedios = tecnicos.stream()
            .map(tecnico -> tecnicoService.ubicacionAprox(tecnico.getId()))
            .collect(Collectors.toCollection(ArrayList::new));
        
        // Obtengo la distancia minima entre la Heladera y un Técnico
        List<Double> distancias = puntosMedios.stream()
            .map(puntoMedio -> AreaService.distanciaEntre2Puntos(puntoMedio, ubicacionHeladera))
            .collect(Collectors.toCollection(ArrayList::new));
        
        OptionalDouble distanciaMinima = distancias.stream().mapToDouble(Double::doubleValue).min();
        
        if (distanciaMinima.isEmpty()) {
            log.log(Level.SEVERE, I18n.getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_err", heladera.getNombre()));
            throw new IllegalArgumentException(I18n.getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_exception"));
        }

        // Si la mónada "Optional distanciaMinima" no está vacía, la desarmo. Sólo puede llegar vacía si no hay Técnicos cuya área de cobertura cubra la Heladera
        Double distancia = distanciaMinima.getAsDouble();
        Integer indexTecnico = distancias.indexOf(distancia);
        
        return tecnicos.get(indexTecnico);
    }
}