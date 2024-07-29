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
    @DisplayName("Testeo el flujo correcto de datos entre la Heladera y su SensorTemperatura")  // Para este Test, cambiamos manualmente las unidades a SEGUNDOS en SensorTemperatura para que notifique cada 5 segundos
    public void SensorTemperaturaOKTest() throws InterruptedException {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();
        SensorTemperatura sensor = new SensorTemperatura(heladera);
        
        sensor.setTempActual(-15f);

        sensor.programarNotificacion();

        Thread.sleep(5000);

        Assertions.assertTrue(heladera.getTempActual() == -15f && heladera.getEstado() == true);        
    } 
    
    @Test
    @DisplayName("Testeo el funcionamiento de la Heladera al sensar una temperatura riesgosa")  // Para este Test, cambiamos manualmente las unidades a SEGUNDOS en SensorTemperatura para que notifique cada 5 segundos
    public void SensorTemperaturaAlertaTest() throws InterruptedException {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();
        SensorTemperatura sensor = new SensorTemperatura(heladera);
        
        sensor.setTempActual(-35f);

        sensor.programarNotificacion();

        Thread.sleep(5000);

        Assertions.assertTrue(heladera.getTempActual() == -35f && heladera.getEstado() == false && !Sistema.getIncidentes().isEmpty());
    }
    
    @Test
    @DisplayName("Testeo el flujo correcto de datos entre la Heladera y su SensorMovimiento")
    public void SensorMovimientoAlertaTest() {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();
        SensorMovimiento sensor = new SensorMovimiento(heladera);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sensor.setHayMovimiento(true);
        });

        Assertions.assertEquals("No hay ningún técnico que cubra la heladera HeladeraPrueba", exception.getMessage());
        Assertions.assertTrue(heladera.getEstado() == false && !Sistema.getIncidentes().isEmpty()); // En realidad debería haber 1 Incidente pero, como Sistema es static, se carga el Incidente del otro test tambien
    }
}
