package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public class RegistroDePersonaEnSituacionVulnerableCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if(args.length != 1 || !(args[0] instanceof Tarjeta)) {
            throw new IllegalArgumentException("Argumentos inválidos para Registrar una Persona Vulnerable");
        }
        
        return new RegistroDePersonaEnSituacionEnVulnerable(colaborador, fechaContribucion, (TarjetaPersonaEnSituacionVulnerable) args[0]);
    }
}
