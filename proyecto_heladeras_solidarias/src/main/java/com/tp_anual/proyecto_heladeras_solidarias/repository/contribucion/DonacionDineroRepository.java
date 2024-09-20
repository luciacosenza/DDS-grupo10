package com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonacionDineroRepository extends JpaRepository<DonacionDinero, Long> {
    
}
