package com.tp_anual_dds.domain.tarjeta;

import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;

public class TarjetaPersonaEnSituacionVulnerableCreator implements TarjetaCreator {
    @Override
    public Tarjeta crearTarjeta(Object titular) {
        if(!(titular instanceof PersonaEnSituacionVulnerable)) {
            throw new IllegalArgumentException("Argumentos inválidos para asignar una Tarjeta de Persona en Situación Vulnerable");
        }

        return new TarjetaPersonaEnSituacionVulnerable((PersonaEnSituacionVulnerable) titular);
    }
}
