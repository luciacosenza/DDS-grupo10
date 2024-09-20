package com.tp_anual.proyecto_heladeras_solidarias.repository.oferta;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    
}