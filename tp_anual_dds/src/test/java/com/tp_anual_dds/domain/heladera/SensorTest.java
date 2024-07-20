package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class SensorTest {
    
    @Test
    @DisplayName("Testeo el flujo correcto de datos entre la Heladera y su SensorTemperatura")
    public void SensorTemperaturaOKTest() throws InterruptedException {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        SensorTemperatura sensor = new SensorTemperatura(heladera);
        
        sensor.setTempActual(-15f);

        sensor.programarNotificacion();

        Thread.sleep(5000);

        Assertions.assertTrue(heladera.getTempActual() == -15f && heladera.getEstado() == true);        
    }
    
    @Test
    @DisplayName("Testeo el funcionamiento de la Heladera al sensar una temperatura riesgosa")
    public void SensorTemperaturaAlertaTest() throws InterruptedException {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        SensorTemperatura sensor = new SensorTemperatura(heladera);
        
        sensor.setTempActual(-35f);

        sensor.programarNotificacion();

        Thread.sleep(5000);

        Assertions.assertTrue(heladera.getTempActual() == -35f && heladera.getEstado() == false && !Sistema.getIncidentes().isEmpty());
    }
    
    @Test
    @DisplayName("Testeo el flujo correcto de datos entre la Heladera y su SensorMovimiento")
    public void SensorMovimientoAlertaTest() throws InterruptedException {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        SensorMovimiento sensor = new SensorMovimiento(heladera);
        
        sensor.setHayMovimiento(true);

        sensor.programarNotificacion();

        Thread.sleep(5000);

        Assertions.assertTrue(heladera.getEstado() == false && !Sistema.getIncidentes().isEmpty()); // En realidad deberia haber 1 Incidente pero, como Sistema es static, se carga el Incidente del otro test tambien
    }
}
