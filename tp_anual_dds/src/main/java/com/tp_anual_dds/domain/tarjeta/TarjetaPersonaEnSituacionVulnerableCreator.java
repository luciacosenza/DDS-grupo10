package com.tp_anual_dds.domain.tarjeta;

import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;

public class TarjetaPersonaEnSituacionVulnerableCreator implements TarjetaCreator {
    @Override
    public Tarjeta crearTarjeta(Object titular) {
        if (!(titular instanceof PersonaEnSituacionVulnerable))
            throw new IllegalArgumentException("Datos inválidos para asignar una tarjeta de persona en situación vulnerable");

        return new TarjetaPersonaEnSituacionVulnerable((PersonaEnSituacionVulnerable) titular);
    }
}
