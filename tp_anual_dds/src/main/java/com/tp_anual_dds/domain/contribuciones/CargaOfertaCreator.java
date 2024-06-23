package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.oferta.Oferta;

public class CargaOfertaCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if(args.length != 1 || !(args[0] instanceof Oferta)) {
            throw new IllegalArgumentException("Argumentos inválidos para realizar una Carga de Oferta");
        }
        
        return new CargaOferta(colaborador, fechaContribucion, (Oferta) args[0]);
    }
}
