package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.sensor.SensorTemperatura;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.SensorTemperaturaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Service
@Log
public class SensorTemperaturaService {

    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final HeladeraService heladeraService;

    private final I18nService i18nService;

    @Getter
    private final Long intervaloNotificacion;

    private final Long tiempoPasadoMaximo;

    public SensorTemperaturaService(SensorTemperaturaRepository vSensorTemperaturaRepository, HeladeraService vHeladeraService, @Value("#{5 * 60 * 1000}") /* Valor arbitrario: 5 minutos en milisegundos */ Long vIntervaloNotificacion, I18nService vI18nService) {
        sensorTemperaturaRepository = vSensorTemperaturaRepository;
        heladeraService = vHeladeraService;
        intervaloNotificacion = vIntervaloNotificacion;
        tiempoPasadoMaximo = vIntervaloNotificacion;    // En este caso, tiempoPasadoMaximo es igual al intervalo, por lo que siempre que se ejecute la tarea programada se chequeará la condición y habrá dos caminos posibles (esto se puede cambiar)

        i18nService = vI18nService;
    }

    public SensorTemperatura obtenerSensorTemperatura(Long sensorTemperaturaId) {
        return sensorTemperaturaRepository.findById(sensorTemperaturaId).orElseThrow(() -> new EntityNotFoundException(i18nService.getMessage("obtenerEntidad_exception")));
    }

    public List<SensorTemperatura> obtenerSensoresTemperatura() {
        return new ArrayList<>(sensorTemperaturaRepository.findAll());
    }

    public SensorTemperatura guardarSensorTemperatura(SensorTemperatura sensorTemperatura) {
        return sensorTemperaturaRepository.save(sensorTemperatura);
    }

    public void actualizarUltimaActualizacion(Long sensorTemperaturaId) {
        SensorTemperatura sensorTemperatura = obtenerSensorTemperatura(sensorTemperaturaId);
        sensorTemperatura.actualizarUltimaActualizacion();
        guardarSensorTemperatura(sensorTemperatura);
    }

    public void setTempActual(Long sensorTemperaturaId, Float vTempActual) {
        SensorTemperatura sensorTemperatura = obtenerSensorTemperatura(sensorTemperaturaId);

        sensorTemperatura.setTempActual(vTempActual);
        actualizarUltimaActualizacion(sensorTemperaturaId);
        guardarSensorTemperatura(sensorTemperatura);    // Guardo el sensorTemperatura porque el setter no está implementado en el service, por lo que no la guarda y hay que hacerlo manualmente
    }

    public Boolean funcionaSensorFisico(Long sensorTemperaturaId) {
        SensorTemperatura sensorTemperatura = obtenerSensorTemperatura(sensorTemperaturaId);

        LocalDateTime ahora = LocalDateTime.now();
        long minutosPasados = ChronoUnit.MINUTES.between(sensorTemperatura.getUltimaActualizacion(), ahora);

        return minutosPasados <= tiempoPasadoMaximo;  // Si pasa más del tiempo estimado, se lanzará una Alerta
    }

    public void notificarHeladera(Long sensorTemperaturaId) {
        SensorTemperatura sensorTemperatura = obtenerSensorTemperatura(sensorTemperaturaId);
        Heladera heladera = sensorTemperatura.getHeladera();

        heladeraService.setTempActual(heladera.getId(), sensorTemperatura.getTempActual());
        heladeraService.guardarHeladera(heladera);

        log.log(Level.INFO, i18nService.getMessage("heladera.SensorTemperatura.notificarHeladera_info", heladera.getNombre()));
    }

    public void notificarFalla(Long sensorTemperaturaId) {
        SensorTemperatura sensorTemperatura = obtenerSensorTemperatura(sensorTemperaturaId);
        Heladera heladera = sensorTemperatura.getHeladera();

        heladeraService.producirAlerta(heladera.getId(), Alerta.TipoAlerta.FALLA_CONEXION);
        heladeraService.guardarHeladera(heladera);
    }

    // Programa la tarea para que se ejecute según el intervalo de notificación
    @Scheduled(fixedRateString = "#{@sensorTemperaturaService.getIntervaloNotificacion()}")
    public void notificar() {
        List<SensorTemperatura> sensoresTemperatura = obtenerSensoresTemperatura();

        for (SensorTemperatura sensorTemperatura : sensoresTemperatura) {
            if (!funcionaSensorFisico(sensorTemperatura.getId()))
                notificarFalla(sensorTemperatura.getId());
            else
                notificarHeladera(sensorTemperatura.getId());
        }
    }
}
