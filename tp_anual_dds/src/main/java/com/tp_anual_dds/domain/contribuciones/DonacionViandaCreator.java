package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.heladera.Vianda;

public class DonacionViandaCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if(args.length != 2 || !(args[0] instanceof Vianda) || !(args[1] instanceof Heladera)) {
            throw new IllegalArgumentException("Argumentos inválidos para realizar una Donación de Vianda");
        }
        
        return new DonacionVianda(colaborador, fechaContribucion, (Vianda) args[0], (Heladera) args[1]);
    }
}
