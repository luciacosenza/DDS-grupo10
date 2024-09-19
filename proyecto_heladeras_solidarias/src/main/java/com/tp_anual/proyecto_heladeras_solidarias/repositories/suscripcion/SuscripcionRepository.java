package com.tp_anual.proyecto_heladeras_solidarias.repositories.suscripcion;

import com.tp_anual.proyecto_heladeras_solidarias.domain.suscripcion.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    
}