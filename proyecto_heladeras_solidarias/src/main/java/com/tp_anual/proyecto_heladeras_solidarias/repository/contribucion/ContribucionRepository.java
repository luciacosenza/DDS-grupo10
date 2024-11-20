package com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContribucionRepository extends JpaRepository<Contribucion, Long> {}
