package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.SensorTemperatura;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.SensorTemperaturaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

@Service
@Log
public class SensorTemperaturaService {

    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final HeladeraService heladeraService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public SensorTemperaturaService(SensorTemperaturaRepository vSensorTemperaturaRepository, HeladeraService vHeladeraService) {
        sensorTemperaturaRepository = vSensorTemperaturaRepository;
        heladeraService = vHeladeraService;
    }

    public SensorTemperatura obtenerSensorTemperatura(Long sensorTemperaturaId) {
        return sensorTemperaturaRepository.findById(sensorTemperaturaId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));

    }

    public SensorTemperatura guardarSensorTemperatura(SensorTemperatura sensorTemperatura) {
        return sensorTemperaturaRepository.save(sensorTemperatura);
    }

    public void setTempActual(Long sensorTemperaturaId, Float vTempActual) {
        SensorTemperatura sensorTemperatura = obtenerSensorTemperatura(sensorTemperaturaId);

        sensorTemperatura.setTempActual(vTempActual);
        sensorTemperatura.actualizarUltimaActualizacion();
        guardarSensorTemperatura(sensorTemperatura);
    }

    public Boolean funcionaSensorFisico(Long sensorTemperaturaId) {
        SensorTemperatura sensorTemperatura = obtenerSensorTemperatura(sensorTemperaturaId);

        LocalDateTime ahora = LocalDateTime.now();
        long minutosPasados = ChronoUnit.MINUTES.between(sensorTemperatura.getUltimaActualizacion(), ahora);

        return minutosPasados <= sensorTemperatura.getMinutosPasadosMaximos();  // Si pasa más del tiempo estimado, se lanzará una Alerta
    }

    public void notificarHeladera(Long sensorTemperaturaId) {
        SensorTemperatura sensorTemperatura = obtenerSensorTemperatura(sensorTemperaturaId);
        HeladeraActiva heladera = sensorTemperatura.getHeladera();

        heladera.setTempActual(sensorTemperatura.getTempActual());
        heladeraService.guardarHeladera(heladera);

        log.log(Level.INFO, I18n.getMessage("heladera.SensorTemperatura.notificarHeladera_info", heladera.getNombre()));
    }

    public void notificarFalla(Long sensorTemperaturaId) {
        SensorTemperatura sensorTemperatura = obtenerSensorTemperatura(sensorTemperaturaId);
        HeladeraActiva heladera = sensorTemperatura.getHeladera();

        heladeraService.producirAlerta(heladera.getId(), Alerta.TipoAlerta.FALLA_CONEXION);
        heladeraService.guardarHeladera(heladera);
    }

    public void programarNotificacion(Long sensorTemperaturaId) {
        SensorTemperatura sensorTemperatura = obtenerSensorTemperatura(sensorTemperaturaId);

        Runnable notificacionTemperatura = () -> {
            if (!funcionaSensorFisico(sensorTemperaturaId))
                notificarFalla(sensorTemperaturaId);

            notificarHeladera(sensorTemperaturaId);
        };

        // Programa la tarea para que se ejecute cada 5 minutos
        scheduler.scheduleAtFixedRate(notificacionTemperatura, 0, sensorTemperatura.getPeriodoNotificacionFalla(), sensorTemperatura.getUnidadPeriodoNotificacionFalla());
    }
}
