package com.tp_anual.proyecto_heladeras_solidarias.repositories.contacto;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.EMail;

public interface EMailRepository extends JpaRepository<EMail, Long> {
    
}