package com.tp_anual.proyecto_heladeras_solidarias.domain.persona;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento;

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

    @Override
    public void obtenerDetalles() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("Documento: " + documento.getTipo() + " | " + documento.getNumero());
        
        if(fechaNacimiento != null)
            System.out.println("Fecha de nacimiento: " + fechaNacimiento);
    }
}
