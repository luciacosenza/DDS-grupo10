package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.SensorMovimientoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.SensorTemperaturaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.IncidenteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SensorTest {
    private static final Logger logger = Logger.getLogger(SensorTest.class.getName());

    @Autowired
    SensorMovimientoService sensorMovimientoService;

    @Autowired
    SensorTemperaturaService sensorTemperaturaService;

    @Autowired
    HeladeraService heladeraService;

    @Autowired
    IncidenteService incidenteService;

    SensorMovimiento sensorMovimiento;
    Heladera heladera;
    SensorTemperatura sensorTemperatura;

    @BeforeEach
    void setup() {
        heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.now() , true);
        heladeraService.guardarHeladera(heladera);
        sensorTemperatura = new SensorTemperatura(heladera);
        sensorTemperaturaService.guardarSensorTemperatura(sensorTemperatura);
        sensorMovimiento = new SensorMovimiento(heladera);
        sensorMovimientoService.guardarSensorMovimiento(sensorMovimiento);

    }
/*
    @Test   // Creo una función similar a la del Sensor (dado que sino habría que cambiar las unidades del Sensor para hacer el Test)
    @DisplayName("Testeo el flujo correcto de datos entre la Heladera y su SensorTemperatura")
    public void SensorTemperaturaOKTest() throws InterruptedException {
        sensorTemperaturaService.setTempActual(sensorTemperatura.getId(), -15f);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        CountDownLatch latch = new CountDownLatch(1);
        Exception[] exceptionHolder = new Exception[1]; // Usamos un Array para tener una final reference no directa al objeto y que nos permita modificarlo en el runnable

        Runnable notificacionTemperatura = () -> {
            if (!sensorTemperaturaService.funcionaSensorFisico(sensorTemperatura.getId()))
                sensorTemperaturaService.notificarFalla(sensorTemperatura.getId());

            try {
                sensorTemperaturaService.notificarHeladera(sensorTemperatura.getId());  // Esto debería provocar la Exception
            } catch (Exception e) {
                exceptionHolder[0] = e; // Captura la Exception
            } finally {
                latch.countDown();
            }
        };

        scheduler.scheduleAtFixedRate(notificacionTemperatura, 0, 5, TimeUnit.MINUTES);

        long timeout = 2;

        if (!latch.await(timeout, TimeUnit.SECONDS)) {    // Esperamos un máximo de 2 segundos
            logger.log(Level.SEVERE, I18n.getMessage("heladera.SensorTest.SensorTemperaturaOKTest_err", sensorTemperatura.getHeladera().getNombre(), timeout));
            throw new IllegalStateException(I18n.getMessage("heladera.SensorTest.SensorTemperaturaOKTest_exception"));
        }

        Assertions.assertTrue(heladera.getTempActual() == -15f && heladera.getEstado() == true);

        scheduler.shutdown();
    }
*/
/*
    @Test
    @DisplayName("Testeo el funcionamiento de la Heladera al sensar una temperatura riesgosa")
    public void SensorTemperaturaAlertaTest() throws InterruptedException {

        sensorTemperaturaService.setTempActual(sensorTemperatura.getId(), -35f);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        CountDownLatch latch = new CountDownLatch(1);
        Exception[] exceptionHolder = new Exception[1]; // Usamos un Array para tener una final reference no directa al objeto y que nos permita modificarlo en el runnable

        Runnable notificacionTemperatura = () -> {
            if (!sensorTemperaturaService.funcionaSensorFisico(sensorTemperatura.getId()))
                sensorTemperaturaService.notificarFalla(sensorTemperatura.getId());

            try {
                sensorTemperaturaService.notificarHeladera(sensorTemperatura.getId()); // Esto debería provocar la Exception
            } catch (Exception e) {
                exceptionHolder[0] = e; // Captura la Exception
            } finally {
               latch.countDown();
            }
       };

        scheduler.scheduleAtFixedRate(notificacionTemperatura, 0, 5, TimeUnit.MINUTES);

        long timeout = 2;

        if (!latch.await(timeout, TimeUnit.SECONDS)) {    // Esperamos un máximo de 2 segundos
            logger.log(Level.SEVERE, I18n.getMessage("heladera.SensorTest.SensorTemperaturaAlertaTest_err", sensorTemperatura.getHeladera().getNombre(), timeout));
            throw new IllegalStateException(I18n.getMessage("heladera.SensorTest.SensorTemperaturaAlertaTest_exception"));    
        }

        scheduler.shutdown();

        Assertions.assertNotNull(exceptionHolder[0]);   // Verificamos que se lanza la Exception del Técnico
        Assertions.assertTrue(heladera.getTempActual() == -35f && heladera.getEstado() == false && !incidenteService.obtenerIncidentes().isEmpty());
    }
    */
    @Test   // Creo una función similar a la del Sensor (dado que sino habría que cambiar las unidades del Sensor para hacer el Test)
    @DisplayName("Testeo el flujo correcto de datos entre la Heladera y su SensorMovimiento")
    public void SensorMovimientoAlertaTest() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sensorMovimientoService.setHayMovimiento(sensorMovimiento.getId() ,true);
        });

        Assertions.assertEquals(I18n.getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_exception"), exception.getMessage());  // Verificamos que se lanza la Exception del Técnico (porque no implementamos que haya uno que la atienda)
        Assertions.assertTrue(heladera.getEstado() == false && !incidenteService.obtenerIncidentes().isEmpty()); // En realidad debería haber 1 Incidente pero, como Sistema es static, se carga el Incidente del otro test también
    }
}
