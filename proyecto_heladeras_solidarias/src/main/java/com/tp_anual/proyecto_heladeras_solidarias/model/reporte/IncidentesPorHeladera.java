package com.tp_anual.proyecto_heladeras_solidarias.model.reporte;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Subselect(
        "SELECT h.id AS heladera_id, h.nombre AS heladera_nombre, COUNT(f.id) AS cantidad_fallas " +
        "FROM heladera AS h " +
        "LEFT JOIN falla_tecnica AS f " +
        "ON h.id = f.heladera " +
        "GROUP BY h.id, h.nombre " +
        "ORDER BY 3 DESC"
)
@Immutable
@Getter
public class IncidentesPorHeladera {

    @Id
    private Long heladeraId;

    private String heladeraNombre;

    private Integer cantidadFallas;
}
