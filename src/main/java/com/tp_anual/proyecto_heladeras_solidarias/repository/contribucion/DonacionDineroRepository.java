package com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonacionDineroRepository extends JpaRepository<DonacionDinero, Long> {

    @Query(nativeQuery = true, name = "DonacionDinero.findDonacionDineroParaPuntos")
    List<DonacionDinero> findDonacionesDineroParaPuntos();
}