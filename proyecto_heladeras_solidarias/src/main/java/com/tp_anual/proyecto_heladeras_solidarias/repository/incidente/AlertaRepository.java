package com.tp_anual.proyecto_heladeras_solidarias.repository.incidente;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByTipoTemperatura();

    List<Alerta> findByTipoFraude();

    List<Alerta> findByTipoFallaConexion();
}