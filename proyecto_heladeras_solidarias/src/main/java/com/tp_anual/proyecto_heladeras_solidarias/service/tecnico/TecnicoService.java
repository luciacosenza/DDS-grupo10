package com.tp_anual.proyecto_heladeras_solidarias.service.tecnico;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.area.Area;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Visita;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tecnico.TecnicoRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.area.AreaService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.logging.Level;

@Service
@Log
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final VisitaService visitaService;
    private final AreaService areaService;
    private final GestorDeVisitas gestorDeVisitas;

    public TecnicoService(TecnicoRepository vTecnicoRepository, VisitaService vVisitaService, AreaService vAreaService,GestorDeVisitas vGestorDeVisitas) {
        tecnicoRepository = vTecnicoRepository;
        visitaService = vVisitaService;
        areaService = vAreaService;
        gestorDeVisitas = vGestorDeVisitas;
    }

    public Tecnico obtenerTecnico(Long tecnicoId) {
        return tecnicoRepository.findById(tecnicoId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public Tecnico guardarTecnico(Tecnico tecnico) {
         return tecnicoRepository.save(tecnico);
    }
    
    public void registrarVisita(Long tecnicoId, LocalDateTime fecha, String descripcion, String foto, Boolean estadoConsulta) {
        Tecnico tecnico = obtenerTecnico(tecnicoId);

        Incidente ultimoIncidenteTratado = tecnico.getPendientes().removeFirst();    // Suponemos que el Técnico atiende los Incidentes por FIFO
        guardarTecnico(tecnico);

        Visita visita = new Visita(tecnico, ultimoIncidenteTratado, fecha, descripcion, foto, estadoConsulta);
        gestorDeVisitas.agregarVisita(visita);
        visitaService.guardarVisita(visita);

        log.log(Level.INFO, I18n.getMessage("tecnico.Tecnico.registrarVisita_info", ultimoIncidenteTratado.getHeladera().getNombre(), ultimoIncidenteTratado.getClass().getSimpleName(), tecnico.getPersona().getNombre(2)));
    }

    // Como convención, para aproximar la Ubicación de un Técnico, vamos a usar el punto medio de su área de cobertura
    public Pair<Double,Double> ubicacionAprox(Long tecnicoId) {
        Tecnico tecnico = obtenerTecnico(tecnicoId);
        Area area = tecnico.getAreaDeCobertura();

        return areaService.puntoMedio(area.getId());
    }
}
