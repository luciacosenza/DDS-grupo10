package com.tp_anual.proyecto_heladeras_solidarias.repositories.incidente;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.FallaTecnica;

public interface FallaTecnicaRepository extends JpaRepository<FallaTecnica, Long> {
    
}