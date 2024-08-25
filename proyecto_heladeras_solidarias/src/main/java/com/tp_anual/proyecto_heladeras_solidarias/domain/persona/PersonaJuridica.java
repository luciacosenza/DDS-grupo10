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

    @Override
    public void obtenerDetalles() {
        System.out.println("Nombre: " + razonSocial);
        
        if(tipo != null)
            System.out.println("Tipo de Persona Jurídica: " + tipo);
        
        if(rubro != null)
            System.out.println("Rubro: " + rubro);
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
