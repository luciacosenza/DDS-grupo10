package com.tp_anual_dds.domain.tarjeta;

import java.util.ArrayList;

import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;

public class TarjetaPersonaEnSituacionVulnerable extends Tarjeta {
    protected ArrayList<UsoTarjeta> usos;
    private PersonaEnSituacionVulnerable titular;

    public TarjetaPersonaEnSituacionVulnerable(String vCodigo, PersonaEnSituacionVulnerable vTitular) {
        codigo = vCodigo;
        titular = vTitular;
        usos = new ArrayList<>();
    }

    @Override
    public PersonaEnSituacionVulnerable getTitular() {
        return titular;
    }

    public void setTitular(PersonaEnSituacionVulnerable vTitular) {
        titular = vTitular;
    }
    
    public void agregarUso(UsoTarjeta uso) {
        usos.add(uso);
    }

    public void resetUsos() {
        usos.clear();
    }

    public Integer cantidadUsos() {
        return usos.size();
    }

    @Override
    public Boolean puedeUsar() {
        return cantidadUsos() < 4 + 2 * titular.getMenoresACargo();
    }
}
