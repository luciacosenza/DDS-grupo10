package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.SensorTemperatura;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

@Service
@Log
public class SensorTemperaturaService {

    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final HeladeraService heladeraService;

    @Getter
    private final Long intervaloNotificacion;

    private final Long tiempoPasadoMaximo;

    public SensorTemperaturaService(SensorTemperaturaRepository vSensorTemperaturaRepository, HeladeraService vHeladeraService, @Value("#{5 * 60 * 1000}") /* Valor arbitrario: 5 minutos en milisegundos */ Long vIntervaloNotificacion) {
        sensorTemperaturaRepository = vSensorTemperaturaRepository;
        heladeraService = vHeladeraService;
        intervaloNotificacion = vIntervaloNotificacion;
        tiempoPasadoMaximo = vIntervaloNotificacion;    // En este caso, tiempoPasadoMaximo es igual al intervalo, por lo que siempre que se ejecute la tarea programada se chequeará la condición y habrá dos caminos posibles (esto se puede cambiar)
    }

    public SensorTemperatura obtenerSensorTemperatura(Long sensorTemperaturaId) {
        return sensorTemperaturaRepository.findById(sensorTemperaturaId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ArrayList<SensorTemperatura> obtenerSensoresTemperatura() {
        return new ArrayList<>(sensorTemperaturaRepository.findAll());
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

        return minutosPasados <= tiempoPasadoMaximo;  // Si pasa más del tiempo estimado, se lanzará una Alerta
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

    @Scheduled(fixedRateString = "#{@sensorTemperaturaService.getIntervaloNotificacion()}")
    public void notificar() {
        ArrayList<SensorTemperatura> sensoresTemperatura = obtenerSensoresTemperatura();

        for (SensorTemperatura sensorTemperatura : sensoresTemperatura) {
            if (!funcionaSensorFisico(sensorTemperatura.getId()))
                notificarFalla(sensorTemperatura.getId());
            else
                notificarHeladera(sensorTemperatura.getId());
        }
    }
}
