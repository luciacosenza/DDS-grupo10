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

    // Método para obtener los detalles "importantes" que identifican de forma sencilla a una Persona, implementado en ambos tipos de Persona
    @Override
    public String getNombre(Integer n) {
        return razonSocial;
    }

    @Override
    public void obtenerDetalles() {
        System.out.println("Nombre: " + razonSocial);
        System.out.println("Tipo de Persona Jurídica: " + tipo);
        System.out.println("Rubro: " + rubro);
    }
}
