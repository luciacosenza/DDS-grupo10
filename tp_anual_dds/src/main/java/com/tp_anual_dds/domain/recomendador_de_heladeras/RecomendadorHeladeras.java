package com.tp_anual_dds.domain.recomendador_de_heladeras;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RecomendadorHeladeras {
    private static final String apiUrl = "https://69cf53c9-48f7-431e-b667-601a587589a8.mock.pstmn.io/recommendation";

    public static List<Map<String, Double>> obtenerValoresDesdeAPI() {
        List<Map<String, Double>> valores = new ArrayList<>();
        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        try {
            HttpResponse<String> respuesta = cliente.send(request, HttpResponse.BodyHandlers.ofString());

            if (respuesta.statusCode() != 200)
                throw new RuntimeException("Error al obtener la respuesta: " + respuesta.statusCode());

            String data = respuesta.body();
            
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<Map<String, Double>>> jsonMap = objectMapper.readValue(data, new TypeReference<Map<String, List<Map<String, Double>>>>() {});
            List<Map<String, Double>> valoresAux = jsonMap.get("puntosRecomendados");
            valores.addAll(valoresAux);
            
        } catch (Exception e) {
            e.printStackTrace();    // TODO: Hay que cambiar esto
        }

        return valores;
    }
}
