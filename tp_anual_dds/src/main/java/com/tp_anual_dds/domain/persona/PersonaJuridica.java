package com.tp_anual_dds.domain.persona;

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
}
