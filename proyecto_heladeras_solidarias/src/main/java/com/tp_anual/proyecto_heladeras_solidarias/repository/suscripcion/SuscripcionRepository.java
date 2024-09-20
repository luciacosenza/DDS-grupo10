package com.tp_anual.proyecto_heladeras_solidarias.repository.suscripcion;

import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    
}