package com.tp_anual.proyecto_heladeras_solidarias.repository.contacto;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.EMail;

public interface EMailRepository extends JpaRepository<EMail, Long> {
    
}