package com.tp_anual.proyecto_heladeras_solidarias.repository.incidente;

import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByTipo(Alerta.TipoAlerta tipoAlerta);



}