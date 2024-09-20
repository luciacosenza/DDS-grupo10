package com.tp_anual.proyecto_heladeras_solidarias.repository.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.SensorTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorTemperaturaRepository extends JpaRepository<SensorTemperatura, Long> {
    
}