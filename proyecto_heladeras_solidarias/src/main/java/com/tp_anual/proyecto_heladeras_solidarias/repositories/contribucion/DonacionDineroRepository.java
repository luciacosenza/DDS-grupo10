package com.tp_anual.proyecto_heladeras_solidarias.repositories.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionDinero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonacionDineroRepository extends JpaRepository<DonacionDinero, Long> {
    
}
