package com.tp_anual_dds.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class HacerseCargoDeHeladeraCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if(args.length != 1 || !(args[0] instanceof HeladeraActiva)) {
            throw new IllegalArgumentException("Argumentos inválidos para Hacerse Cargo de una Heladera");
        }
        
        return new HacerseCargoDeHeladera(colaborador, fechaContribucion, (HeladeraActiva) args[0]);
    }
}
