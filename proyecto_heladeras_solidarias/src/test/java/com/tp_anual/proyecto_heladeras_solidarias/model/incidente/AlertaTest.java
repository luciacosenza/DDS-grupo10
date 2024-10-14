package com.tp_anual.proyecto_heladeras_solidarias.model.incidente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.SensorMovimientoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.SensorTemperaturaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.IncidenteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.SensorMovimiento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.SensorTemperatura;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta.TipoAlerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AlertaTest {
    private static final Logger logger = Logger.getLogger(AlertaTest.class.getName());

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

    @Test
    @DisplayName("Testeo la carga de Alerta de Fraude")
    public void CargaAlertaFraudeTest() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sensorMovimientoService.setHayMovimiento(sensorMovimiento.getId() ,true);
        });

        ArrayList<Incidente> alertasDelSistema = incidenteService.obtenerIncidentes().stream()
            .filter(incidente -> incidente instanceof Alerta)
            .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Alerta> alertasDeFraudeDelSistema = alertasDelSistema.stream()
            .map(alerta -> (Alerta) alerta)
            .filter(alerta -> alerta.getTipo() == TipoAlerta.FRAUDE)
            .collect(Collectors.toCollection(ArrayList::new));

        Assertions.assertEquals(I18n.getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_exception"), exception.getMessage());  // Verificamos que se lanza la Exception del Técnico (porque no implementamos que haya uno que la atienda)
        Assertions.assertTrue(heladera.getEstado() == false && alertasDeFraudeDelSistema.size() == 1);
    }

    @Test   // Llamo directo al método notificarFalla()
    @DisplayName("Testeo la carga de Alerta de Falla de Conexión")
    public void CargaAlertaFallaConexionTest() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sensorTemperaturaService.notificarFalla(sensorTemperatura.getId());
        });

        ArrayList<Incidente> alertasDelSistema = incidenteService.obtenerIncidentes().stream()
            .filter(incidente -> incidente instanceof Alerta)
            .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Alerta> alertasDeFallaConexionDelSistema = alertasDelSistema.stream()
            .map(alerta -> (Alerta) alerta)
            .filter(alerta -> alerta.getTipo() == TipoAlerta.FALLA_CONEXION)
            .collect(Collectors.toCollection(ArrayList::new));

        Assertions.assertEquals(I18n.getMessage("ubicador.ubicadorTecnico.obtenerTecnicoCercanoA_exception"), exception.getMessage());  // Verificamos que se lanza la Exception del Técnico (porque no implementamos que haya uno que la atienda)
        Assertions.assertTrue(heladera.getEstado() == false && alertasDeFallaConexionDelSistema.size() == 1);
    }
}
