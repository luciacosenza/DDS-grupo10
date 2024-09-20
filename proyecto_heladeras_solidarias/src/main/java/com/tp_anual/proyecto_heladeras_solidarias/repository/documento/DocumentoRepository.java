package com.tp_anual.proyecto_heladeras_solidarias.repository.documento;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    
}