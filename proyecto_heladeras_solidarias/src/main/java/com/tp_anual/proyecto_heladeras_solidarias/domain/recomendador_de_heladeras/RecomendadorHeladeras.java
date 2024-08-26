package com.tp_anual.proyecto_heladeras_solidarias.domain.recomendador_de_heladeras;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp_anual.proyecto_heladeras_solidarias.message_loader.I18n;

public class RecomendadorHeladeras {
    private static final Logger logger = Logger.getLogger(RecomendadorHeladeras.class.getName());
    private static final String apiUrl = "https://69cf53c9-48f7-431e-b667-601a587589a8.mock.pstmn.io/recommendation";

    public static List<Map<String, Double>> obtenerValoresDesdeAPI() {
        List<Map<String, Double>> valores = new ArrayList<>();
        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        try {
            HttpResponse<String> respuesta = cliente.send(request, HttpResponse.BodyHandlers.ofString());

            if (respuesta.statusCode() != 200) {
                logger.log(Level.SEVERE, I18n.getMessage("recomendador_de_heladeras.RecomendadorHeladeras.obtenerValoresDesdeAPI_err_obtencion_respuesta", respuesta.statusCode()));
                throw new RuntimeException(I18n.getMessage("recomendador_de_heladeras.RecomendadorHeladeras.obtenerValoresDesdeAPI_exception_obtencion_respuesta", respuesta.statusCode()));
            }

            String data = respuesta.body();
            
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<Map<String, Double>>> jsonMap = objectMapper.readValue(data, new TypeReference<Map<String, List<Map<String, Double>>>>() {});
            List<Map<String, Double>> valoresAux = jsonMap.get("puntosRecomendados");
            valores.addAll(valoresAux);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, I18n.getMessage("recomendador_de_heladeras.RecomendadorHeladeras.obtenerValoresDesdeAPI_err_comunicacion_api"));
            throw new RuntimeException(I18n.getMessage("recomendador_de_heladeras.RecomendadorHeladeras.obtenerValoresDesdeAPI_exception_comunicacion_api"));
        }

        return valores;
    }
}
