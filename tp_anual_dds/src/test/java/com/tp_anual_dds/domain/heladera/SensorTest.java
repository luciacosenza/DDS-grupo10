package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class SensorTest {
    
    @Test   // Creo una función similar a la del Sensor (dado que sino habría que cambiar las unidades del Sensor para hacer el Test)
    @DisplayName("Testeo el flujo correcto de datos entre la Heladera y su SensorTemperatura")
    public void SensorTemperaturaOKTest() throws InterruptedException {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();

        SensorTemperatura sensor = new SensorTemperatura(heladera);
        sensor.darDeAlta();
        
        sensor.setTempActual(-15f);

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

        Assertions.assertTrue(heladera.getTempActual() == -15f && heladera.getEstado() == true);

        scheduler.shutdown();
    } 
    
    @Test
    @DisplayName("Testeo el funcionamiento de la Heladera al sensar una temperatura riesgosa")
    public void SensorTemperaturaAlertaTest() throws InterruptedException {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();

        SensorTemperatura sensor = new SensorTemperatura(heladera);
        sensor.darDeAlta();
        
        sensor.setTempActual(-35f);

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

        Assertions.assertNotNull(exceptionHolder[0]);   // Verificamos que se lanza la Exception del Técnico
        Assertions.assertTrue(heladera.getTempActual() == -35f && heladera.getEstado() == false && !Sistema.getIncidentes().isEmpty());
    }
    
    @Test   // Creo una función similar a la del Sensor (dado que sino habría que cambiar las unidades del Sensor para hacer el Test)
    @DisplayName("Testeo el flujo correcto de datos entre la Heladera y su SensorMovimiento")
    public void SensorMovimientoAlertaTest() {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();

        SensorMovimiento sensor = new SensorMovimiento(heladera);
        sensor.darDeAlta();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sensor.setHayMovimiento(true);
        });

        Assertions.assertEquals("No hay ningún técnico que cubra la heladera HeladeraPrueba", exception.getMessage());  // Verificamos que se lanza la Exception del Técnico
        Assertions.assertTrue(heladera.getEstado() == false && !Sistema.getIncidentes().isEmpty()); // En realidad debería haber 1 Incidente pero, como Sistema es static, se carga el Incidente del otro test tambien
    }
}
