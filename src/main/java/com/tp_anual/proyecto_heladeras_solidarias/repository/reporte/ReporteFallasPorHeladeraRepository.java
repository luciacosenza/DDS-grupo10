package com.tp_anual.proyecto_heladeras_solidarias.repository.reporte;

import com.tp_anual.proyecto_heladeras_solidarias.model.reporte.FallasPorHeladera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporteFallasPorHeladeraRepository extends JpaRepository<FallasPorHeladera, Long> {
}
