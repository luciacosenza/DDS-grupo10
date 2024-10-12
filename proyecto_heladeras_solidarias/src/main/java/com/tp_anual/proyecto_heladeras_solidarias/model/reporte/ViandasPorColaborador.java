package com.tp_anual.proyecto_heladeras_solidarias.model.reporte;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "reporte_viandas_por_colaborador")
@Getter
public class ViandasPorColaborador {

    @Id
    private Long colaboradorId;

    private String colaboradorNombre;

    private Integer cantidadViandas;
}
