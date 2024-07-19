package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual_dds.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;

public class RegistroDePersonaEnSituacionVulnerableCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if(args.length != 2 || !(args[0] instanceof PersonaEnSituacionVulnerable) || !(args[1] instanceof TarjetaPersonaEnSituacionVulnerable)) {
            throw new IllegalArgumentException("Argumentos inválidos para Registrar una Persona En Situacion Vulnerable");
        }
        
        return new RegistroDePersonaEnSituacionEnVulnerable(colaborador, fechaContribucion, (PersonaEnSituacionVulnerable) args[0], (TarjetaPersonaEnSituacionVulnerable) args[1]);
    }
}
