package com.tp_anual.proyecto_heladeras_solidarias.domain.contacto;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MedioDeContacto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    public abstract void contactar(String asunto, String cuerpo);
}
