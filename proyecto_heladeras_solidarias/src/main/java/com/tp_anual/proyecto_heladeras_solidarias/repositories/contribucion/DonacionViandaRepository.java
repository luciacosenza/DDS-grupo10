package com.tp_anual.proyecto_heladeras_solidarias.repositories.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionVianda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonacionViandaRepository extends JpaRepository<DonacionVianda, Long> {
    
}
