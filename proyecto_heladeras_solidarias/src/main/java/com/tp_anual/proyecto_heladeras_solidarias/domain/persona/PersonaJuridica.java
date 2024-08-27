package com.tp_anual.proyecto_heladeras_solidarias.domain.persona;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class PersonaJuridica extends Persona {
    private String razonSocial;
    private TipoPersonaJuridica tipo;
    private String rubro;

    public enum TipoPersonaJuridica {
        GUBERNAMENTAL,
        ONG,
        EMPRESA,
        INSTITUCION
    }

    public PersonaJuridica(String vRazonSocial, TipoPersonaJuridica vTipo, String vRubro) {
        razonSocial = vRazonSocial;
        tipo = vTipo;
        rubro = vRubro;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public TipoPersonaJuridica getTipo() {
        return tipo;
    }

    public String getRubro() {
        return rubro;
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
