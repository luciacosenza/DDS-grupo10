package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.SensorTemperatura;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.SensorTemperaturaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class SensorTemperaturaService {

    private final SensorTemperaturaRepository sensorTemperaturaRepository;

    public SensorTemperaturaService(SensorTemperaturaRepository vSensorTemperaturaRepository) {
        sensorTemperaturaRepository = vSensorTemperaturaRepository;
    }

    public SensorTemperatura obtenerSensorTemperatura(Long sensorTemperaturaId) {
        return sensorTemperaturaRepository.findById(sensorTemperaturaId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));

    }

    public SensorTemperatura guardarSensorTemperatura(SensorTemperatura sensorTemperatura) {
        return sensorTemperaturaRepository.save(sensorTemperatura);
    }
}
