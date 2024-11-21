package com.tp_anual.proyecto_heladeras_solidarias.service.reporte;

import com.tp_anual.proyecto_heladeras_solidarias.model.reporte.FallasPorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.reporte.MovimientosViandaPorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.reporte.ViandasPorColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.repository.reporte.ReporteFallasPorHeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.reporte.ReporteMovimientosViandaPorHeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.reporte.ReporteViandasPorColaboradorRepository;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class ReporteService {

    private final ReporteFallasPorHeladeraRepository reporteFallasPorHeladeraRepository;
    private final ReporteMovimientosViandaPorHeladeraRepository reporteMovimientosViandaPorHeladeraRepository;
    private final ReporteViandasPorColaboradorRepository reporteViandasPorColaboradorRepository;
    private final JdbcTemplate jdbcTemplate;

    public ReporteService(ReporteFallasPorHeladeraRepository vReporteFallasPorHeladeraRepository, ReporteMovimientosViandaPorHeladeraRepository vReporteMovimientosViandaPorHeladeraRepository, ReporteViandasPorColaboradorRepository vReporteViandasPorColaboradorRepository, JdbcTemplate vJdbcTemplate) {
        reporteFallasPorHeladeraRepository = vReporteFallasPorHeladeraRepository;
        reporteMovimientosViandaPorHeladeraRepository = vReporteMovimientosViandaPorHeladeraRepository;
        reporteViandasPorColaboradorRepository = vReporteViandasPorColaboradorRepository;
        jdbcTemplate = vJdbcTemplate;
    }


    public List<FallasPorHeladera> obtenerReporteFallasPorHeladera() {
        return new ArrayList<>(reporteFallasPorHeladeraRepository.findAll());
    }

    public List<MovimientosViandaPorHeladera> obtenerReporteMovimientosViandaPorHeladera() {
        return new ArrayList<>(reporteMovimientosViandaPorHeladeraRepository.findAll());
    }

    public List<ViandasPorColaborador> obtenerReporteViandasPorColaborador() {
        return new ArrayList<>(reporteViandasPorColaboradorRepository.findAll());
    }

    // Programo la tarea para ejecutarse todos meses, el primer día, a las 00.00 hs
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generarReportesMensuales() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW reporte_incidentes_por_heladera;");
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW reporte_movimientos_vianda_por_heladera;");
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW reporte_viandas_por_colaborador;");
    }
}
