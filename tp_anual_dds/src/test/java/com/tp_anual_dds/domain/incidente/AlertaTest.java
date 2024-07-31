package com.tp_anual_dds.domain.incidente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.SensorMovimiento;
import com.tp_anual_dds.domain.heladera.SensorTemperatura;
import com.tp_anual_dds.domain.incidente.Alerta.TipoAlerta;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class AlertaTest {
    
    @Test
    @DisplayName("Testeo la carga de Alerta de Temperatura")
    public void AlertaTemperaturaTest() throws InterruptedException {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();
        SensorTemperatura sensor = new SensorTemperatura(heladera);
        
        sensor.setTempActual(6f);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        CountDownLatch latch = new CountDownLatch(1);
        Exception[] exceptionHolder = new Exception[1]; // Usamos un Array para tener una final reference no directa al objeto y que nos permita modificarlo en el runnable

        Runnable notificacionTemperatura = () -> {
            if (!sensor.funcionaSensorFisico())
                sensor.notificarFalla();

            try {
                sensor.notificarHeladera(); // Esto debería provocar la Exception
            } catch (Exception e) {
                exceptionHolder[0] = e; // Captura la Exception
            } finally {
                latch.countDown();
            }
        };

        scheduler.scheduleAtFixedRate(notificacionTemperatura, 0, 5, TimeUnit.MINUTES);

        if (!latch.await(2, TimeUnit.SECONDS))   // Esperamos un maximo de 2 segundos
            throw new IllegalStateException("El cálculo de puntos no terminó a tiempo");

        scheduler.shutdown();

        ArrayList<Incidente> alertasDelSistema = Sistema.getIncidentes().stream()
            .filter(incidente -> incidente instanceof Alerta)
            .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Alerta> alertasDeTemperaturaDelSistema = alertasDelSistema.stream()
            .map(alerta -> (Alerta) alerta)
            .filter(alerta -> alerta.getTipo() == TipoAlerta.TEMPERATURA)
            .collect(Collectors.toCollection(ArrayList::new));

        Assertions.assertNotNull(exceptionHolder[0]);   // Verificamos que se lanza la Exception del Técnico
        Assertions.assertTrue(heladera.getTempActual() == 6f && heladera.getEstado() == false && alertasDeTemperaturaDelSistema.size() == 1);
    }

    @Test
    @DisplayName("Testeo la carga de Alerta de Fraude")
    public void AlertaFraudeTest() {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();
        SensorMovimiento sensor = new SensorMovimiento(heladera);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sensor.setHayMovimiento(true);
        });

        ArrayList<Incidente> alertasDelSistema = Sistema.getIncidentes().stream()
            .filter(incidente -> incidente instanceof Alerta)
            .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Alerta> alertasDeFraudeDelSistema = alertasDelSistema.stream()
            .map(alerta -> (Alerta) alerta)
            .filter(alerta -> alerta.getTipo() == TipoAlerta.FRAUDE)
            .collect(Collectors.toCollection(ArrayList::new));

        Assertions.assertEquals("No hay ningún técnico que cubra la heladera HeladeraPrueba", exception.getMessage());  // Verificamos que se lanza la Exception del Técnico
        Assertions.assertTrue(heladera.getEstado() == false && alertasDeFraudeDelSistema.size() == 1); // En realidad debería haber 1 Incidente pero, como Sistema es static, se carga el Incidente del otro test tambien
    }
}
