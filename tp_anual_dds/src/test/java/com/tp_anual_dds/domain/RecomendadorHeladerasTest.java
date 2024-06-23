package com.tp_anual_dds.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tp_anual_dds.domain.recomendador.RecomendadorHeladeras;

public class RecomendadorHeladerasTest {
    
    @Test
    @DisplayName("Testeo que funcione el Recomendador")
    public void RecommendTest() {

        List<Map<String, Double>> resultado = RecomendadorHeladeras.obtenerValoresDesdeAPI();

        List<Map<String, Double>> valoresEsperados = Arrays.asList(
            Map.of("latitud", -34.6030, "longitud", -58.3800),
            Map.of("latitud", -34.6040, "longitud", -58.3820),
            Map.of("latitud", -34.6050, "longitud", -58.3830)
        );

        Assertions.assertEquals(valoresEsperados, resultado);
    }
}
