package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.Heladera;

public class HacerseCargoDeHeladeraCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if(args.length != 1 || !(args[0] instanceof Heladera)) {
            throw new IllegalArgumentException("Argumentos inválidos para Hacerse Cargo de una Heladera");
        }
        
        return new HacerseCargoDeHeladera(colaborador, fechaContribucion, (Heladera) args[0]);
    }
}
