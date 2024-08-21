package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;

public class TarjetaPersonaEnSituacionVulnerableCreator implements TarjetaCreator {
    @Override
    public Tarjeta crearTarjeta(Object titular) {
        if (!(titular instanceof PersonaEnSituacionVulnerable))
            throw new IllegalArgumentException("Datos inválidos para asignar una tarjeta de persona en situación vulnerable");

        return new TarjetaPersonaEnSituacionVulnerable((PersonaEnSituacionVulnerable) titular);
    }
}
