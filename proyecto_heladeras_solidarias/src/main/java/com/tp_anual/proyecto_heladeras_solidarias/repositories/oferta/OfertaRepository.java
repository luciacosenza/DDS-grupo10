package com.tp_anual.proyecto_heladeras_solidarias.repositories.oferta;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.domain.oferta.Oferta;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    
}