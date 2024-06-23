package com.tp_anual_dds.domain.tarjeta;

import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;

public class TarjetaPersonaEnSituacionVulnerableCreator implements TarjetaCreator {
    @Override
    public Tarjeta crearTarjeta(String codigo, Object titular) {
        if(!(titular instanceof PersonaEnSituacionVulnerable)) {
            throw new IllegalArgumentException("Argumentos inválidos para asignar una Tarjeta de Persona en Situación Vulnerable");
        }

        // En el futuro, podemos agregar una validacion para el codigo, suponiendo que este sea distinto en su estructura para Colaboradores o P.E.S.V.

        return new TarjetaPersonaEnSituacionVulnerable(codigo, (PersonaEnSituacionVulnerable) titular);
    }
}
