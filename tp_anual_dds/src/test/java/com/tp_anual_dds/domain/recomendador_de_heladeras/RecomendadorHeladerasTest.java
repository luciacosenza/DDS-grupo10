package com.tp_anual_dds.domain.recomendador_de_heladeras;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecomendadorHeladerasTest {
    
    @Test
    @DisplayName("Testeo que funcione la Mock-Api")
    public void RecommendTest() {

        List<Map<String, Double>> resultado = RecomendadorHeladeras.obtenerValoresDesdeAPI();

        Double latitudEsperada1 = -34.6030;
        Double latitudEsperada2 = -34.6040;
        Double latitudEsperada3 = -34.6050;
        Double longitudEsperada1 = -58.3800;
        Double longitudEsperada2 = -58.3820;
        Double longitudEsperada3 = -58.3830;

        List<Map<String, Double>> valoresEsperados = Arrays.asList(
            Map.of("latitud", latitudEsperada1, "longitud", longitudEsperada1),
            Map.of("latitud", latitudEsperada2, "longitud", longitudEsperada2),
            Map.of("latitud", latitudEsperada3, "longitud", longitudEsperada3)
        );

        Assertions.assertEquals(valoresEsperados, resultado);
    }
}
