package com.tp_anual.proyecto_heladeras_solidarias.repositories.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AccionHeladera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccionHeladeraRepository extends JpaRepository<AccionHeladera, Long> {
    
}