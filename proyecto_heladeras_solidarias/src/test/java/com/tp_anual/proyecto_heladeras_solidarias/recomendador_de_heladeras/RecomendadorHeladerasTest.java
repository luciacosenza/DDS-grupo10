package com.tp_anual.proyecto_heladeras_solidarias.recomendador_de_heladeras;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.tp_anual.proyecto_heladeras_solidarias.service.recomendador_de_heladeras.RecomendadorHeladeras;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecomendadorHeladerasTest {
    
    @Test
    @DisplayName("Testeo que funcione la Mock-Api")
    public void RecommendadorHeladerasMockApiTest() {
        RecomendadorHeladeras recomendadorHeladeras = RecomendadorHeladeras.getInstance();

        List<Map<String, String>> resultado = recomendadorHeladeras.obtenerValoresDesdeAPI();

        String latitudEsperada1 = "-34.61372944931148";
        String latitudEsperada2 = "-34.61257748945765";
        String latitudEsperada3 = "-34.61429395523585";
        String latitudEsperada4 = "-34.630582472067225";
        String latitudEsperada5 = "-34.619455224239715";
        String latitudEsperada6 = "-34.628534865351185";
        String latitudEsperada7 = "-34.622321759871504";
        
        String longitudEsperada1 = "-58.40965258478539";
        String longitudEsperada2 = "-58.399577544965695";
        String longitudEsperada3 = "-58.42308821071305";
        String longitudEsperada4 = "-58.39345198287705";
        String longitudEsperada5 = "-58.391557666580994";
        String longitudEsperada6 = "-58.37654941808667";
        String longitudEsperada7 = "-58.42898576858127";

        String direccionEsperada1 = "Moreno 3099, C1209ABE Cdad. Autónoma de Buenos Aires";
        String direccionEsperada2 = "Matheu 240, C1082 Cdad. Autónoma de Buenos Aires";
        String direccionEsperada3 = "Av. Hipólito Yrigoyen 3999, C1181 Cdad. Autónoma de Buenos Aires";
        String direccionEsperada4 = "Pichincha 1890, C1245 Cdad. Autónoma de Buenos Aires";
        String direccionEsperada5 = "Av. Entre Ríos 946, C1080ABQ Cdad. Autónoma de Buenos Aires";
        String direccionEsperada6 = "Av. Montes de Oca 40, C1270 Cdad. Autónoma de Buenos Aires";
        String direccionEsperada7 = "BYN, Av. Juan Bautista Alberdi 100, C1424 C1424BYN, Cdad. Autónoma de Buenos Aires";

        List<Map<String, String>> valoresEsperados = Arrays.asList(
            Map.of("latitud", latitudEsperada1, "longitud", longitudEsperada1, "direccion", direccionEsperada1),
            Map.of("latitud", latitudEsperada2, "longitud", longitudEsperada2, "direccion", direccionEsperada2),
            Map.of("latitud", latitudEsperada3, "longitud", longitudEsperada3, "direccion", direccionEsperada3),
            Map.of("latitud", latitudEsperada4, "longitud", longitudEsperada4, "direccion", direccionEsperada4),
            Map.of("latitud", latitudEsperada5, "longitud", longitudEsperada5, "direccion", direccionEsperada5),
            Map.of("latitud", latitudEsperada6, "longitud", longitudEsperada6, "direccion", direccionEsperada6),
            Map.of("latitud", latitudEsperada7, "longitud", longitudEsperada7, "direccion", direccionEsperada7)
        );

        Assertions.assertEquals(valoresEsperados, resultado);
    }
}
