package com.tp_anual.proyecto_heladeras_solidarias.model.reporte;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "reporte_movimientos_vianda_por_heladera")
@Getter
public class MovimientosViandaPorHeladera {

    @Id
    private Long heladeraId;

    private String heladeraNombre;

    private Integer total_ingresos;

    private Integer total_egresos;
}
