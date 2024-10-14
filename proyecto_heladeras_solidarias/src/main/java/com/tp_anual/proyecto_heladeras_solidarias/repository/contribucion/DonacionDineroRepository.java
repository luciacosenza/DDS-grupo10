package com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonacionDineroRepository extends JpaRepository<DonacionDinero, Long> {

    @Query("SELECT d FROM DonacionDinero d ")   // // TODO: Arreglar la Query
    List<DonacionDinero> findDonacionesDineroParaCalcular();
}