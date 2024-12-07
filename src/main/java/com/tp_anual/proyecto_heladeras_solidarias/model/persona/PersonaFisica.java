package com.tp_anual.proyecto_heladeras_solidarias.model.persona;

import java.time.LocalDate;
import java.util.Locale;

import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
public class PersonaFisica extends Persona {

    @Setter
    private String nombre;

    @Setter
    private String apellido;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "documento")
    private Documento documento;    // final

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;  // final

    public PersonaFisica() {
        super();
        documento = new Documento();
    }

    public PersonaFisica(String vNombre, String vApellido, Documento vDocumento, LocalDate vFechaDeNacimiento) {
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
        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String logMessage1 = messageSource.getMessage("persona.PersonaFisica.obtenerDetalles_out_nombre", new Object[] {nombre}, Locale.getDefault());
        String logMessage2 = messageSource.getMessage("persona.PersonaFisica.obtenerDetalles_out_apellido", new Object[] {apellido}, Locale.getDefault());
        String logMessage3 = messageSource.getMessage("persona.PersonaFisica.obtenerDetalles_out_documento", new Object[] {documento.getTipo(), documento.getNumero()}, Locale.getDefault());

        System.out.println(logMessage1);
        System.out.println(logMessage2);
        System.out.println(logMessage3);
        
        if(fechaNacimiento != null) {
            String logMessage4 = messageSource.getMessage("persona.PersonaFisica.obtenerDetalles_out_fecha_nacimiento", new Object[]{fechaNacimiento}, Locale.getDefault());
            System.out.println(logMessage4);
        }
    }
}
