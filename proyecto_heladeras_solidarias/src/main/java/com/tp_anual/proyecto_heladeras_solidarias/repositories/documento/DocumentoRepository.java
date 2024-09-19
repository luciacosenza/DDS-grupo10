package com.tp_anual.proyecto_heladeras_solidarias.repositories.documento;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    
}