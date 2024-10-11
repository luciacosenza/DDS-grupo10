package com.tp_anual.proyecto_heladeras_solidarias.model.area;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Area {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private final Double x1;

    private final Double y1;

    private final Double x2;
    
    private final Double y2;

    public Area(Double vX1, Double vY1, Double vX2, Double vY2) {
        x1 = vX1;
        y1 = vY1;
        x2 = vX2;
        y2 = vY2;
    }
}
