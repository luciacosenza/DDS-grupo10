package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.SensorMovimiento;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.SensorMovimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

@Service
@Log
public class SensorMovimientoService {

    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final HeladeraService heladeraService;

    public SensorMovimientoService(SensorMovimientoRepository vSensorMovimientoRepository, HeladeraService vHeladeraService) {
        sensorMovimientoRepository = vSensorMovimientoRepository;
        heladeraService = vHeladeraService;
    }

    public SensorMovimiento obtenerSensorMovimiento(Long sensorMovimientoId) {
        return sensorMovimientoRepository.findById(sensorMovimientoId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public SensorMovimiento guardarSensorMovimiento(SensorMovimiento sensorMovimiento) {
        return sensorMovimientoRepository.save(sensorMovimiento);
    }

    public void notificarHeladera(Long sensorMovimientoId) {
        SensorMovimiento sensorMovimiento = obtenerSensorMovimiento(sensorMovimientoId);
        HeladeraActiva heladera = sensorMovimiento.getHeladera();

        heladeraService.producirAlerta(heladera.getId(), Alerta.TipoAlerta.FRAUDE);
        heladeraService.guardarHeladera(heladera);

        log.log(Level.INFO, I18n.getMessage("heladera.SensorMovimiento.notificarHeladera_info", heladera.getNombre()));
    }

    // De esta forma, el Sensor físico le envía cada cierto tiempo si hay movimiento o no (y el lógico se encarga de chequear si la señal fue positiva o negativa)
    public void setHayMovimiento(Long sensorMovimientoId, Boolean vHayMovimiento) {
        SensorMovimiento sensorMovimiento = obtenerSensorMovimiento(sensorMovimientoId);

        sensorMovimiento.setHayMovimiento(vHayMovimiento);
        guardarSensorMovimiento(sensorMovimiento);

        if(sensorMovimiento.getHayMovimiento())
            notificarHeladera(sensorMovimientoId);
    }
    // Otra forma sería que el Sensor físico mande la señal sólo si hay movimiento, no haciendo falta que chequee el lógico
}
