package com.tp_anual.proyecto_heladeras_solidarias.repositories.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarjetaPersonaEnSituacionVulnerableRepository extends JpaRepository<TarjetaPersonaEnSituacionVulnerable, String> {
    
}