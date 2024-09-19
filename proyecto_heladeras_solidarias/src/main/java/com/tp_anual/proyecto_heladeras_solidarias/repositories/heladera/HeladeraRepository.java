package com.tp_anual.proyecto_heladeras_solidarias.repositories.heladera;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Heladera;

public interface HeladeraRepository extends JpaRepository<Heladera, Long> {
    
}