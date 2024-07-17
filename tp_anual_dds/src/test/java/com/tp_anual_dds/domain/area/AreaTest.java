package com.tp_anual_dds.domain.area;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AreaTest {
    
    @Test
    @DisplayName("Testeo que las coordenadas no se encuentren cubiertas por el rango latitudinal del Area")
    public void NoEstaDentroEnXTest() {
        Double latitud = 23547d;
        Double longitud = 56984d;

        Double xVertice1 = 24000d;
        Double yVertice1 = 56000d;
        Double xVertice2 = 25000d;
        Double yVertice2 = 57000d;

        Area area = new Area(xVertice1, yVertice1, xVertice2, yVertice2);

        Assertions.assertFalse(area.estaDentro(latitud, longitud));
    }

    @Test
    @DisplayName("Testeo que las coordenadas no se encuentren cubiertas por el rango longitudinal del Area")
    public void NoEstaDentroEnYTest() {
        Double latitud = 23547d;
        Double longitud = 56984d;

        Double xVertice1 = 23000d;
        Double yVertice1 = 55000d;
        Double xVertice2 = 24000d;
        Double yVertice2 = 56000d;

        Area area = new Area(xVertice1, yVertice1, xVertice2, yVertice2);

        Assertions.assertFalse(area.estaDentro(latitud, longitud));
    }

    @Test
    @DisplayName("Testeo que las coordenadas no se encuentren cubiertas por el rango latitudinal del Area (usando coordenadas negativas)")
    public void NoEstaDentroEnXNegativoTest() {
        Double latitud = -23547d;
        Double longitud = -56984d;

        Double xVertice1 = -23000d;
        Double yVertice1 = -57000d;
        Double xVertice2 = -22000d;
        Double yVertice2 = -56000d;

        Area area = new Area(xVertice1, yVertice1, xVertice2, yVertice2);

        Assertions.assertFalse(area.estaDentro(latitud, longitud));
    }

    @Test
    @DisplayName("Testeo que las coordenadas no se encuentren cubiertas por el rango longitudinal del Area (usando coordenadas negativas)")
    public void NoEstaDentroEnYNegativoTest() {
        Double latitud = -23547d;
        Double longitud = -56984d;

        Double xVertice1 = -24000d;
        Double yVertice1 = -58000d;
        Double xVertice2 = -23000d;
        Double yVertice2 = -57000d;

        Area area = new Area(xVertice1, yVertice1, xVertice2, yVertice2);

        Assertions.assertFalse(area.estaDentro(latitud, longitud));
    }

    @Test
    @DisplayName("Testeo que las coordenadas se encuentren cubiertas por el Area")
    public void EstaDentroTest() {
        Double latitud = 23547d;
        Double longitud = 56984d;

        Double xVertice1 = 23000d;
        Double yVertice1 = 56000d;
        Double xVertice2 = 24000d;
        Double yVertice2 = 57000d;

        Area area = new Area(xVertice1, yVertice1, xVertice2, yVertice2);

        Assertions.assertTrue(area.estaDentro(latitud, longitud));
    }

    @Test
    @DisplayName("Testeo que las coordenadas se encuentren cubiertas por el Area (usando coordenadas negativas)")
    public void EstaDentroNegativoTest() {
        Double latitud = -23547d;
        Double longitud = -56984d;

        Double xVertice1 = -24000d;
        Double yVertice1 = -57000d;
        Double xVertice2 = -23000d;
        Double yVertice2 = -56000d;

        Area area = new Area(xVertice1, yVertice1, xVertice2, yVertice2);

        Assertions.assertTrue(area.estaDentro(latitud, longitud));
    }
}
