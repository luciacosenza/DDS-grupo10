package com.tp_anual.proyecto_heladeras_solidarias.repositories.tecnico;

import com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitaRepository extends JpaRepository<Visita, Long> {
    
}