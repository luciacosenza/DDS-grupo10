package com.tp_anual.proyecto_heladeras_solidarias.repositories.ubicacion;

import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    
}