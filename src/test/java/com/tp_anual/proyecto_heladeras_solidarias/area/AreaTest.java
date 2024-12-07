package com.tp_anual.proyecto_heladeras_solidarias.area;

import com.tp_anual.proyecto_heladeras_solidarias.model.area.Area;
import com.tp_anual.proyecto_heladeras_solidarias.service.area.AreaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AreaTest {

    @Autowired
    private AreaService areaService;

    Double latitud;
    Double longitud;
    Double latitudN;
    Double longitudN;
    Double latitudC;
    Double longitudC;
    Double latitudCN;
    Double longitudCN;
    Long areaId;
    Long areaIdN;
    Long areaIdC;
    Long areaIdCN;

    @BeforeEach
    void setup() {
        latitud = 23547d;
        longitud = 56984d;

        latitudN = -23547d;
        longitudN = -56984d;

        latitudC = 23547d;
        longitudC = 56984d;

        latitudCN = -23547d;
        longitudCN = -56984d;

        Double xVertice1 = 24000d;
        Double yVertice1 = 57000d;
        Double xVertice2 = 25000d;
        Double yVertice2 = 58000d;

        Double xVertice1N = -25000d;
        Double yVertice1N = -58000d;
        Double xVertice2N = -24000d;
        Double yVertice2N = -57000d;

        Double xVertice1C = 23000d;
        Double yVertice1C = 56000d;
        Double xVertice2C = 24000d;
        Double yVertice2C = 57000d;

        Double xVertice1CN = -24000d;
        Double yVertice1CN = -57000d;
        Double xVertice2CN = -23000d;
        Double yVertice2CN = -56000d;

        Area area = new Area(xVertice1, yVertice1, xVertice2, yVertice2);
        Area areaN = new Area(xVertice1N, yVertice1N, xVertice2N, yVertice2N);
        Area areaC = new Area(xVertice1C, yVertice1C, xVertice2C, yVertice2C);
        Area areaCN = new Area(xVertice1CN, yVertice1CN, xVertice2CN, yVertice2CN);

        areaId = areaService.guardarArea(area).getId();
        areaIdN = areaService.guardarArea(areaN).getId();
        areaIdC = areaService.guardarArea(areaC).getId();
        areaIdCN = areaService.guardarArea(areaCN).getId();
    }

    @Test
    @DisplayName("Testeo que las coordenadas no se encuentren cubiertas por el rango latitudinal del Area")
    public void NoEstaDentroEnXTest() {
        Assertions.assertFalse(areaService.estaDentro(areaId, latitud, longitud));
    }

    @Test
    @DisplayName("Testeo que las coordenadas no se encuentren cubiertas por el rango longitudinal del Area")
    public void NoEstaDentroEnYTest() {
        Assertions.assertFalse(areaService.estaDentro(areaId, latitud, longitud));
    }

    @Test
    @DisplayName("Testeo que las coordenadas no se encuentren cubiertas por el rango latitudinal del Area (usando coordenadas negativas)")
    public void NoEstaDentroEnXNegativoTest() {
        Assertions.assertFalse(areaService.estaDentro(areaIdN, latitudN, longitudN));
    }

    @Test
    @DisplayName("Testeo que las coordenadas no se encuentren cubiertas por el rango longitudinal del Area (usando coordenadas negativas)")
    public void NoEstaDentroEnYNegativoTest() {
        Assertions.assertFalse(areaService.estaDentro(areaIdN, latitudN, longitudN));
    }

    @Test
    @DisplayName("Testeo que las coordenadas se encuentren cubiertas por el Area")
    public void EstaDentroTest() {
        Assertions.assertTrue(areaService.estaDentro(areaIdC, latitud, longitud));
    }

    @Test
    @DisplayName("Testeo que las coordenadas se encuentren cubiertas por el Area (usando coordenadas negativas)")
    public void EstaDentroNegativoTest() {
        Assertions.assertTrue(areaService.estaDentro(areaIdCN, latitud, longitud));
    }
}
