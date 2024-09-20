package com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.permisos_de_apertura.PermisoApertura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoAperturaRepository extends JpaRepository<PermisoApertura, Long> {
    
}