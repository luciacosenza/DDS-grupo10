package com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.HacerseCargoDeHeladera;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HacerseCargoDeHeladeraRepository extends JpaRepository<HacerseCargoDeHeladera, Long> {
    
    @Query(nativeQuery = true, name = "HacerseCargoDeHeladera.findHacerseCargoDeHeladeraParaPuntos")
    List<HacerseCargoDeHeladera> findHacerseCargoDeHeladeraParaPuntos();
}