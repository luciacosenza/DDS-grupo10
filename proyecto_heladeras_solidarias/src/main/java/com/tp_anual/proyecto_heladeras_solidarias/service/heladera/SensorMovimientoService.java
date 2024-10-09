package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.SensorMovimiento;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.SensorMovimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class SensorMovimientoService {

    private final SensorMovimientoRepository sensorMovimientoRepository;

    public SensorMovimientoService(SensorMovimientoRepository vSensorMovimientoRepository) {
        sensorMovimientoRepository = vSensorMovimientoRepository;
    }

    public SensorMovimiento obtenerSensorMovimiento(Long sensorMovimientoId) {
        return sensorMovimientoRepository.findById(sensorMovimientoId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public SensorMovimiento guardarSensorMovimiento(SensorMovimiento sensorMovimiento) {
        return sensorMovimientoRepository.save(sensorMovimiento);
    }
}
