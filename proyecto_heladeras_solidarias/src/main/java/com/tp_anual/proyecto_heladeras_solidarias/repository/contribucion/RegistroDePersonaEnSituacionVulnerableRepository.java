package com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroDePersonaEnSituacionVulnerableRepository extends JpaRepository<RegistroDePersonaEnSituacionVulnerable, Long> {

    List<RegistroDePersonaEnSituacionVulnerable> findByYaSumoPuntosFalse();
}
