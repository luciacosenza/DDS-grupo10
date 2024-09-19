package com.tp_anual.proyecto_heladeras_solidarias.repositories.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
    
}
