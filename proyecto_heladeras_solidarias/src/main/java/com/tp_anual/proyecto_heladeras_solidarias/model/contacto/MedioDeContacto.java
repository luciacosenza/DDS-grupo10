package com.tp_anual.proyecto_heladeras_solidarias.model.contacto;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public abstract class MedioDeContacto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public MedioDeContacto() {}

    public String getNombre() {
        String nombre = getClass().getSimpleName();

        return switch (nombre) {
            case "EMail" -> "Correo Electrónico";
            case "Telefono" -> "Télefono";
            case "Telegram" -> "Telegram";
            case "WhatsApp" -> "WhatsApp";
            default -> "";
        };
    }
}
