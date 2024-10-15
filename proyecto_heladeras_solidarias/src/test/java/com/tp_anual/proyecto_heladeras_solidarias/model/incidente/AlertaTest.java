package com.tp_anual.proyecto_heladeras_solidarias.model.incidente;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.SensorMovimientoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.SensorTemperaturaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.AlertaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.SensorMovimiento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.SensorTemperatura;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AlertaTest {

    @Autowired
    HeladeraService heladeraService;

    @Autowired
    SensorTemperaturaService sensorTemperaturaService;

    @Autowired
    SensorMovimientoService sensorMovimientoService;

    @Autowired
    AlertaService alertaService;

    Heladera heladera;
    SensorTemperatura sensorTemperatura;
    SensorMovimiento sensorMovimiento;

    Long heladeraId;
    Long sensorTemperaturaId;
    Long sensorMovimientoId;

    @BeforeEach
    void setup() {
        heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.now() , true);
        heladeraId = heladeraService.guardarHeladera(heladera).getId();

        sensorTemperatura = new SensorTemperatura(heladera);
        sensorMovimiento = new SensorMovimiento(heladera);

        sensorTemperaturaId = sensorTemperaturaService.guardarSensorTemperatura(sensorTemperatura).getId();
        sensorMovimientoId = sensorMovimientoService.guardarSensorMovimiento(sensorMovimiento).getId();
    }

    /*
    @Test   // Creo una función similar a la del Sensor (dado que sino habría que cambiar las unidades del Sensor para hacer el Test)
    @DisplayName("Testeo la carga de Alerta de Temperatura")
    public void CargaAlertaTemperaturaTest() throws InterruptedException {
        sensorTemperaturaService.setTempActual(sensorTemperatura.getId(), 6f);

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

        if (!latch.await(timeout, TimeUnit.SECONDS)) {  // Esperamos un máximo de 2 segundos
            logger.log(Level.SEVERE, I18n.getMessage("incidente.AlertaTest.CargaAlertaTemperaturaTest_err", sensorTemperatura.getHeladera().getNombre(), timeout));
            throw new IllegalStateException(I18n.getMessage("incidente.AlertaTest.CargaAlertaTemperaturaTest_exception"));
        }
        
        scheduler.shutdown();

        ArrayList<Incidente> alertasDelSistema = incidenteService.obtenerIncidentes().stream()
            .filter(incidente -> incidente instanceof Alerta)
            .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Alerta> alertasDeTemperaturaDelSistema = alertasDelSistema.stream()
            .map(alerta -> (Alerta) alerta)
            .filter(alerta -> alerta.getTipo() == TipoAlerta.TEMPERATURA)
            .collect(Collectors.toCollection(ArrayList::new));

        Assertions.assertNotNull(exceptionHolder[0]);   // Verificamos que se lanza la Exception del Técnico
        Assertions.assertTrue(heladera.getTempActual() == 6f && heladera.getEstado() == false && alertasDeTemperaturaDelSistema.size() == 1);
    }
    */

    @Test   // Llamo directo al método setHayMovimiento()
    @DisplayName("Testeo la carga de Alerta de Fraude")
    public void CargaAlertaFraudeTest() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> sensorMovimientoService.setHayMovimiento(sensorMovimiento.getId() ,true));

        ArrayList<Alerta> alertasFraude = alertaService.obtenerAlertasFraude();

        Assertions.assertEquals(I18n.getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_exception"), exception.getMessage());  // Verificamos que se lanza la Exception del Técnico (porque no implementamos que haya uno que la atienda)
        Assertions.assertTrue(!heladera.getEstado() && alertasFraude.size() == 1);
    }

    @Test   // Llamo directo al método notificarFalla()
    @DisplayName("Testeo la carga de Alerta de Falla de Conexión")
    public void CargaAlertaFallaConexionTest() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> sensorTemperaturaService.notificarFalla(sensorTemperatura.getId()));

        ArrayList<Alerta> alertasFallaConexion = alertaService.obtenerAlertasFallaConexion();

        Assertions.assertEquals(I18n.getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_exception"), exception.getMessage());  // Verificamos que se lanza la Exception del Técnico (porque no implementamos que haya uno que la atienda)
        Assertions.assertTrue(!heladera.getEstado() && alertasFallaConexion.size() == 1);
    }
}
