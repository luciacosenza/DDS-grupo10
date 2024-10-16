package com.tp_anual.proyecto_heladeras_solidarias.model.persona;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class PersonaFisica extends Persona {

    @Setter
    private String nombre;

    @Setter
    private String apellido;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "documento")
    private Documento documento;    // final

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaNacimiento;  // final

    public PersonaFisica() {
        super();
    }

    public PersonaFisica(String vNombre, String vApellido, Documento vDocumento, LocalDateTime vFechaDeNacimiento) {
        super();
        nombre = vNombre;
        apellido = vApellido;
        documento = vDocumento;
        fechaNacimiento = vFechaDeNacimiento;
    }

    // Método para obtener los detalles "importantes" que identifican de forma sencilla a una Persona, implementado en ambos tipos de Persona
    @Override
    public String getNombre(Integer n) {
        return switch (n) {
            case 0 -> nombre;
            case 1 -> apellido;
            default -> nombre + " " + apellido;
        };
    }

    @Override
    public void obtenerDetalles() {
        System.out.println(I18n.getMessage("persona.PersonaFisica.obtenerDetalles_out_nombre", nombre));
        System.out.println(I18n.getMessage("persona.PersonaFisica.obtenerDetalles_out_apellido", apellido));
        System.out.println(I18n.getMessage("persona.PersonaFisica.obtenerDetalles_out_documento", documento.getTipo(), documento.getNumero()));
        
        if(fechaNacimiento != null)
            System.out.println(I18n.getMessage("persona.PersonaFisica.obtenerDetalles_out_fecha_nacimiento", fechaNacimiento));
    }
}
