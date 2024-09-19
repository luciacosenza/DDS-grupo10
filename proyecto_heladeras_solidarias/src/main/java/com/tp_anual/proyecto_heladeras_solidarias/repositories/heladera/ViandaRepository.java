package com.tp_anual.proyecto_heladeras_solidarias.repositories.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Vianda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViandaRepository extends JpaRepository<Vianda, Long> {
    
}