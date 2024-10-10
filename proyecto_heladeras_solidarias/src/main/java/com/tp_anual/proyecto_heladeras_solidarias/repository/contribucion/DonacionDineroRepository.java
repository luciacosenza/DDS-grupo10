package com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonacionDineroRepository extends JpaRepository<DonacionDinero, Long> {

    @Query("SELECT d FROM DonacionDinero d " +
            "WHERE NOT d.yaSumoPuntos = false AND d.frecuencia = 'UNICA_VEZ') " +
            "OR (CURRENT_DATE - FUNCTION('DATE', d.ultimaActualizacion) >= 7 AND d.frecuencia = 'SEMANAL') " +
            "OR (CURRENT_DATE - FUNCTION('DATE', d.ultimaActualizacion) >= 30 AND d.frecuencia = 'MENSUAL') " +
            "OR (CURRENT_DATE - FUNCTION('DATE', d.ultimaActualizacion) >= 180 AND d.frecuencia = 'SEMESTRAL') " +
            "OR (CURRENT_DATE - FUNCTION('DATE', d.ultimaActualizacion) >= 365 AND d.frecuencia = 'ANUAL')")
    List<DonacionDinero> findDonacionesDineroParaCalcular();
}