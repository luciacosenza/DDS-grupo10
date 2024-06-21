package com.tp_anual_dds.domain;

import java.util.ArrayList;

public class TarjetaPersonaEnSituacionVulnerable extends Tarjeta {
    private PersonaEnSituacionVulnerable titular;

    public TarjetaPersonaEnSituacionVulnerable(String vCodigo, PersonaEnSituacionVulnerable vTitular) {
        codigo = vCodigo;
        usos = new ArrayList<>();
        titular = vTitular;
    }

    public PersonaEnSituacionVulnerable getTitular() {
        return titular;
    }

    @Override
    public Boolean puedeUsar() {
        return cantidadUsos() < 4 + 2 * titular.getMenoresACargo();
    }
}
