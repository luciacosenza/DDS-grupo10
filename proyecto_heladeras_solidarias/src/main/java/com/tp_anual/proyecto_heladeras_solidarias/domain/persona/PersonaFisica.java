package com.tp_anual.proyecto_heladeras_solidarias.domain.persona;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class PersonaFisica extends Persona {
    private String nombre;
    private String apellido;
    private Documento documento;
    private LocalDateTime fechaNacimiento;

    public PersonaFisica(String vNombre, String vApellido, Documento vDocumento, LocalDateTime vFechaDeNacimiento) {
        nombre = vNombre;
        apellido = vApellido;
        documento = vDocumento;
        fechaNacimiento = vFechaDeNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Documento getDocumento() {
        return documento;
    }

    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
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
