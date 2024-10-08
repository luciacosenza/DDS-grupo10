package com.tp_anual.proyecto_heladeras_solidarias.repository.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import org.springframework.data.jpa.repository.JpaRepository;

// Esta interfaz sólo sirve para hacer consultas sobre las subclases de MediosDeContacto de manera general, no para consultar por MedioDeContacto en sí, ya que no tiene una entidad asociada
public interface MedioDeContactoRepository extends JpaRepository<MedioDeContacto, Long> {

}
