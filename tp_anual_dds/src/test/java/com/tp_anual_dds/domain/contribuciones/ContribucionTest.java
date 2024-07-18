package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.documento.Documento.Sexo;
import com.tp_anual_dds.domain.documento.Documento.TipoDocumento;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class ContribucionTest {
    
    @Test   // Funciona dudoso (hay que chequearlo)
    @DisplayName("Testeo el cálculo de Puntos utilizando un Scheduler")
    public void CalcularPuntosTest() throws InterruptedException {  // Testeamos una version modificada de calcularPuntos(), dado que la original cuenta con periodos muy largos de ejecucion como para ser testeada
        ColaboradorHumano colaborador = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        final LocalDateTime[] ultimaActualizacion = { LocalDateTime.now() };    // Usamos un Array para tener una final reference no directa al objeto y que nos permita modificarlo en el lambda
        Integer monto = 10;
        Double multiplicador_puntos = 1d; 
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final int maxIterations = 5;
        final int[] iterations = {0};   // Lo mismo que con ultimaActualizacion

        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            Long periodosPasados = ChronoUnit.SECONDS.between(ultimaActualizacion[0], ahora);
            if (periodosPasados >= 5) {
                colaborador.sumarPuntos(monto * multiplicador_puntos);
                ultimaActualizacion[0] = ahora;
            }
            iterations[0]++;
            if (iterations[0] > maxIterations) {
                scheduler.shutdown();
            }
        };

        scheduler.scheduleAtFixedRate(calculoPuntos, 0, 5, TimeUnit.SECONDS);
        
        Thread.sleep(60000);

        Assertions.assertEquals(30d, colaborador.getPuntos());
    }
}