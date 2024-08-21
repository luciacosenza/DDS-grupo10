package com.tp_anual.proyecto_heladeras_solidarias.domain.persona;

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
}
