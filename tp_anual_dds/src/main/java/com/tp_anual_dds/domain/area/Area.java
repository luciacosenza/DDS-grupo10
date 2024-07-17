package com.tp_anual_dds.domain.area;

public class Area {
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;

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
}
