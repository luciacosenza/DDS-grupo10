package com.tp_anual.proyecto_heladeras_solidarias.repositories.area;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.domain.area.Area;

public interface AreaRepository extends JpaRepository<Area, Long> {
    
}