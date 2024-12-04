package com.tp_anual.proyecto_heladeras_solidarias.model.reporte;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Subselect(
        "SELECT h.id AS heladera_id, h.nombre AS heladera_nombre, COUNT(d1.id) + COUNT(d2.id) AS total_ingresos, COUNT(d3.id) AS total_egresos " +
        "FROM heladera AS h " +
        "LEFT JOIN donacion_vianda AS d1 " +
        "ON h.id = d1.heladera " +
        "LEFT JOIN distribucion_viandas AS d2 " +
        "ON h.id = d2.heladera_destino " +
        "LEFT JOIN distribucion_viandas AS d3 " +
        "ON h.id = d3.heladera_origen " +
        "GROUP BY h.id, h.nombre " +
        "ORDER BY 3 DESC, 4 DESC"
)
@Immutable
@Getter
public class MovimientosViandaPorHeladera {

    @Id
    private Long heladeraId;

    private String heladeraNombre;

    private Integer totalIngresos;

    private Integer totalEgresos;
}
