package com.tp_anual.proyecto_heladeras_solidarias.service.reporte;

import com.tp_anual.proyecto_heladeras_solidarias.model.reporte.IncidentesPorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.reporte.MovimientosViandaPorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.reporte.ViandasPorColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.repository.reporte.ReporteIncidentesPorHeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.reporte.ReporteMovimientosViandaPorHeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.reporte.ReporteViandasPorColaboradorRepository;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Log
public class ReporteService {

    private final ReporteIncidentesPorHeladeraRepository reporteIncidentesPorHeladeraRepository;
    private final ReporteMovimientosViandaPorHeladeraRepository reporteMovimientosViandaPorHeladeraRepository;
    private final ReporteViandasPorColaboradorRepository reporteViandasPorColaboradorRepository;
    private final JdbcTemplate jdbcTemplate;

    public ReporteService(ReporteIncidentesPorHeladeraRepository vReporteIncidentesPorHeladeraRepository, ReporteMovimientosViandaPorHeladeraRepository vReporteMovimientosViandaPorHeladeraRepository, ReporteViandasPorColaboradorRepository vReporteViandasPorColaboradorRepository, JdbcTemplate vJdbcTemplate) {
        reporteIncidentesPorHeladeraRepository = vReporteIncidentesPorHeladeraRepository;
        reporteMovimientosViandaPorHeladeraRepository = vReporteMovimientosViandaPorHeladeraRepository;
        reporteViandasPorColaboradorRepository = vReporteViandasPorColaboradorRepository;
        jdbcTemplate = vJdbcTemplate;
    }


    public ArrayList<IncidentesPorHeladera> obtenerReporteIncidentesPorHeladera() {
        return new ArrayList<>(reporteIncidentesPorHeladeraRepository.findAll());
    }

    public ArrayList<MovimientosViandaPorHeladera> obtenerReporteMovimientosViandaPorHeladera() {
        return new ArrayList<>(reporteMovimientosViandaPorHeladeraRepository.findAll());
    }

    public ArrayList<ViandasPorColaborador> obtenerReporteViandasPorColaborador() {
        return new ArrayList<>(reporteViandasPorColaboradorRepository.findAll());
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void generarReportesMensuales() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW reporte_incidentes_por_heladera;");
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW reporte_movimientos_vianda_por_heladera;");
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW reporte_viandas_por_colaborador;");
    }
}
