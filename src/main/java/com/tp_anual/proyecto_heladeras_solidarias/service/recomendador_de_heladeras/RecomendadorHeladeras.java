package com.tp_anual.proyecto_heladeras_solidarias.service.recomendador_de_heladeras;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class RecomendadorHeladeras {

    private final String apiUrl = "https://74d20d86-99bd-452c-8c65-77ce0eac7fef.mock.pstmn.io/recommendation";

    private final I18nService i18nService;

    public RecomendadorHeladeras(I18nService vI18nService) {
        i18nService = vI18nService;
    }

    public List<Map<String, String>> obtenerValoresDesdeAPI() {
        List<Map<String, String>> valores = new ArrayList<>();
        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        try {
            HttpResponse<String> respuesta = cliente.send(request, HttpResponse.BodyHandlers.ofString());

            if (respuesta.statusCode() != 200) {
                log.log(Level.SEVERE, i18nService.getMessage("recomendador_de_heladeras.RecomendadorHeladeras.obtenerValoresDesdeAPI_err_obtencion_respuesta", respuesta.statusCode()));
                throw new RuntimeException(i18nService.getMessage("recomendador_de_heladeras.RecomendadorHeladeras.obtenerValoresDesdeAPI_exception_obtencion_respuesta", respuesta.statusCode()));
            }

            String data = respuesta.body();
            
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<Map<String, String>>> jsonMap = objectMapper.readValue(data, new TypeReference<Map<String, List<Map<String, String>>>>() {});
            List<Map<String, String>> valoresAux = jsonMap.get("puntosRecomendados");
            valores.addAll(valoresAux);
            
        } catch (Exception e) {
            log.log(Level.SEVERE, i18nService.getMessage("recomendador_de_heladeras.RecomendadorHeladeras.obtenerValoresDesdeAPI_err_comunicacion_api"));
            throw new RuntimeException(i18nService.getMessage("recomendador_de_heladeras.RecomendadorHeladeras.obtenerValoresDesdeAPI_exception_comunicacion_api"));
        }

        return valores;
    }
}
