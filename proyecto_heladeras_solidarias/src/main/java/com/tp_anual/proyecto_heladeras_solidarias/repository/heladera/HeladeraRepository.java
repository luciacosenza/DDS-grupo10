package com.tp_anual.proyecto_heladeras_solidarias.repository.heladera;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HeladeraRepository extends JpaRepository<Heladera, Long> {

    @Query("SELECT h.nombre FROM Heladera h")
    List<String> findAllNombres();
}