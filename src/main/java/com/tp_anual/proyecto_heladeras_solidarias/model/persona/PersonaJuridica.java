package com.tp_anual.proyecto_heladeras_solidarias.model.persona;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Entity
@Getter
public class PersonaJuridica extends Persona {

    @Setter
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    @Setter
    private TipoPersonaJuridica tipo;   // final

    @Setter
    private String rubro;   // final

    public enum TipoPersonaJuridica {
        GUBERNAMENTAL,
        ONG,
        EMPRESA,
        INSTITUCION
    }

    public PersonaJuridica() {
        super();
    }

    public PersonaJuridica(String vRazonSocial, TipoPersonaJuridica vTipo, String vRubro) {
        super();
        razonSocial = vRazonSocial;
        tipo = vTipo;
        rubro = vRubro;
    }

    // Método para obtener los detalles "importantes" que identifican de forma sencilla a una Persona, implementado en ambos tipos de Persona
    @Override
    public String getNombre(Integer n) {
        return razonSocial;
    }

    @Override
    public void obtenerDetalles() {
        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String logMessage1 = messageSource.getMessage("persona.PersonaJuridica.obtenerDetalles_out_razon_social", new Object[] {razonSocial}, Locale.getDefault());

        System.out.println(logMessage1);
        
        if (tipo != null) {
            String logMessage2 = messageSource.getMessage("persona.PersonaFisica.obtenerDetalles_out_tipo", new Object[] {tipo}, Locale.getDefault());
            System.out.println(logMessage2);
        }
        
        if (rubro != null) {
            String logMessage3 = messageSource.getMessage("persona.PersonaFisica.obtenerDetalles_out_rubro", new Object[] {rubro}, Locale.getDefault());
            System.out.println(logMessage3);
        }
    }
}
