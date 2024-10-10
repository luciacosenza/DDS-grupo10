package com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.CargaOferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CargaOfertaRepository extends JpaRepository<CargaOferta, Long> {

    List<CargaOferta> findByYaSumoPuntosFalse();
}
