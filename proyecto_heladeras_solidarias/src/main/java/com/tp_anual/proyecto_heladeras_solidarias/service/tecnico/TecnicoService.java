package com.tp_anual.proyecto_heladeras_solidarias.service.tecnico;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.area.Area;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Visita;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tecnico.TecnicoRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.area.AreaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.usuario.UsuarioService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Service
@Log
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final UsuarioService usuarioService;
    private final VisitaService visitaService;
    private final AreaService areaService;

    public TecnicoService(TecnicoRepository vTecnicoRepository, UsuarioService vUsuarioService, VisitaService vVisitaService, AreaService vAreaService) {
        tecnicoRepository = vTecnicoRepository;
        usuarioService = vUsuarioService;
        visitaService = vVisitaService;
        areaService = vAreaService;
    }

    public Tecnico obtenerTecnico(Long tecnicoId) {
        return tecnicoRepository.findById(tecnicoId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public List<Tecnico> obtenerTecnicos() {
        return new ArrayList<>(tecnicoRepository.findAll());
    }

    public Tecnico guardarTecnico(Tecnico tecnico) {
         return tecnicoRepository.saveAndFlush(tecnico);
    }

    public Tecnico asignarUsuario(Long tecnicoId, Usuario usuario) {
        Tecnico tecnico = obtenerTecnico(tecnicoId);
        tecnico.setUsuario(usuario);

        return guardarTecnico(tecnico);
    }

    public void agregarAPendientes(Long tecnicoId, Incidente incidente) {
        Tecnico tecnico = obtenerTecnico(tecnicoId);
        tecnico.agregarAPendientes(incidente);
        guardarTecnico(tecnico);
    }

    public void registrarVisita(Long tecnicoId, LocalDateTime fecha, String descripcion, String foto, Boolean estadoConsulta) {
        Tecnico tecnico = obtenerTecnico(tecnicoId);

        Incidente ultimoIncidenteTratado = tecnico.getPendientes().removeFirst();    // Suponemos que el Técnico atiende los Incidentes por FIFO
        guardarTecnico(tecnico);

        Visita visita = new Visita(tecnico, ultimoIncidenteTratado, fecha, descripcion, foto, estadoConsulta, estadoConsulta);
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
