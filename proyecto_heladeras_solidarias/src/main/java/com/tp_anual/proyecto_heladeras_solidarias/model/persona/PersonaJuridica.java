package com.tp_anual.proyecto_heladeras_solidarias.model.persona;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class PersonaJuridica extends Persona {

    @Setter
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    @Setter
    private TipoPersonaJuridica tipo;

    @Setter
    private String rubro;

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
        System.out.println(I18n.getMessage("persona.PersonaJuridica.obtenerDetalles_out_razon_social", razonSocial));
        
        if (tipo != null)
            System.out.println(I18n.getMessage("persona.PersonaJuridica.obtenerDetalles_out_tipo", tipo));
        
        if (rubro != null)
            System.out.println(I18n.getMessage("persona.PersonaJuridica.obtenerDetalles_out_rubro", rubro));
    }
}
