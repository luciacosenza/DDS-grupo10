package com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.HacerseCargoDeHeladera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HacerseCargoDeHeladeraRepository extends JpaRepository<HacerseCargoDeHeladera, Long> {

    @Query("SELECT h FROM HacerseCargoDeHeladera h " +
            "WHERE (CURRENT_DATE - FUNCTION('DATE', h.ultimaActualizacion) >= 30)")
    List<HacerseCargoDeHeladera> findHacerseCargoDeHeladeras();
}

