package com.tp_anual.proyecto_heladeras_solidarias.model.reporte;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "reporte_incidentes_por_heladera")
@Getter
public class IncidentesPorHeladera {

    @Id
    private Long heladeraId;

    private String heladeraNombre;

    private Integer cantidadIncidentes;
}
