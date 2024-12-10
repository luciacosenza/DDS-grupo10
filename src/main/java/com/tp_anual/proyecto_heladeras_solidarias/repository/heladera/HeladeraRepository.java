package com.tp_anual.proyecto_heladeras_solidarias.repository.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeladeraRepository extends JpaRepository<Heladera, Long> {

    List<Heladera> findHeladerasParaColaborador(@Param("colaborador") Long colaboradorId);
}