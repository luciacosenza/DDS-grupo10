package com.tp_anual.proyecto_heladeras_solidarias.repository.contacto;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.WhatsApp;

public interface WhatsAppRepository extends JpaRepository<WhatsApp, Long> {
    
}