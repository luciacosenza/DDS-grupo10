package com.tp_anual.proyecto_heladeras_solidarias.repositories.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.SensorTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorTemperaturaRepository extends JpaRepository<SensorTemperatura, Long> {
    
}