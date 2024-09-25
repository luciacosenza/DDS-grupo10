package com.tp_anual.proyecto_heladeras_solidarias.model.area;

import org.apache.commons.lang3.tuple.Pair;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Area {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    private final Double x1;

    @NotNull
    private final Double y1;

    @NotNull
    private final Double x2;
    
    @NotNull
    private final Double y2;

    public Area(Double vX1, Double vY1, Double vX2, Double vY2) {
        x1 = vX1;
        y1 = vY1;
        x2 = vX2;
        y2 = vY2;
    }

    private Boolean entraEnX(Double x) {
        return x > x1 && x < x2;
    }

    private Boolean entraEnY(Double y) {
        return y > y1 && y < y2;
    }

    public Boolean estaDentro(Double x, Double y) {
        return entraEnX(x) && entraEnY(y); 
    }

    public Pair<Double, Double> puntoMedio() {
        Double xM = (x2 - x1) / 2;
        Double yM = (y2 - y1) / 2;

        return Pair.of(xM, yM);
    }
    
    // Este método es estático dado que es propio de un Área, pero no debería hacer falta llamar a un Objeto de este tipo para usarlo
    public static Double distanciaEntre2Puntos(Pair<Double, Double> tupla1, Pair<Double, Double> tupla2) { 
        Double xT1 = tupla1.getLeft();
        Double yT1 = tupla1.getRight();
        Double xT2 = tupla2.getLeft();
        Double yT2 = tupla2.getRight();

        return Math.sqrt(Math.pow(xT1 - xT2, 2) + Math.pow(yT1 - yT2, 2));
    }
}
