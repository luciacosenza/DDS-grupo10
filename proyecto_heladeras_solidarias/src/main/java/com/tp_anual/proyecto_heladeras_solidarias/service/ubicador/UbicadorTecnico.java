package com.tp_anual.proyecto_heladeras_solidarias.service.ubicador;

import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.tp_anual.proyecto_heladeras_solidarias.service.area.AreaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.IncidenteService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tecnico.TecnicoService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.tuple.Pair;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import org.springframework.stereotype.Service;

@Service
@Log
public class UbicadorTecnico {
    private final IncidenteService incidenteService;
    private final AreaService areaService;
    private final TecnicoService tecnicoService;

    public UbicadorTecnico(IncidenteService vIncidenteService, AreaService vAreaService, TecnicoService vTecnicoService) {
        incidenteService = vIncidenteService;
        areaService = vAreaService;
        tecnicoService = vTecnicoService;
    }

    public Tecnico obtenerTecnicoCercanoA(Long incidenteId) {
        Incidente incidente = incidenteService.obtenerIncidente(incidenteId);

        //Obtengo la Ubicación de la Heladera en cuestión
        HeladeraActiva heladera = incidente.getHeladera();
        Pair<Double, Double> ubicacionHeladera = Pair.of(heladera.getUbicacion().getLatitud(), heladera.getUbicacion().getLongitud());
        
        // Obtengo la lista de Técnicos que tengan a la Heladera en su área de cobertura
        ArrayList<Tecnico> tecnicos = Sistema.getTecnicos().stream()
            .filter(tecnico -> areaService.estaDentro(tecnico.getAreaDeCobertura().getId(), ubicacionHeladera.getLeft(), ubicacionHeladera.getRight()))
            .collect(Collectors.toCollection(ArrayList::new));
        
        // Obtengo las Ubicaciones de los Técnicos
        ArrayList<Pair<Double, Double>> puntosMedios = tecnicos.stream()
            .map(tecnico -> tecnicoService.ubicacionAprox(tecnico.getId()))
            .collect(Collectors.toCollection(ArrayList::new));
        
        // Obtengo la distancia minima entre la Heladera y un Técnico
        ArrayList<Double> distancias = puntosMedios.stream()
            .map(puntoMedio -> AreaService.distanciaEntre2Puntos(puntoMedio, ubicacionHeladera))
            .collect(Collectors.toCollection(ArrayList::new));
        
        OptionalDouble distanciaMinima = distancias.stream().mapToDouble(Double::doubleValue).min();
        
        if (distanciaMinima.isEmpty()) {
            log.log(Level.SEVERE, I18n.getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_err", heladera.getNombre()));
            throw new IllegalArgumentException(I18n.getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_exception"));
        }

        // Si la mónada "Optional distanciaMinima" no esté vacía, la desarmo. Sólo puede llegar vacía si no hay Técnicos cuya área de cobertura cubra la Heladera
        Double distancia = distanciaMinima.getAsDouble();
        Integer indexTecnico = distancias.indexOf(distancia);
        
        return tecnicos.get(indexTecnico);
    }
}