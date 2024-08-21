package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.oferta.Oferta;

public class CargaOfertaCreator implements ContribucionCreator {
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args) {
        if (args.length != 1 ||
            !(args[0] instanceof Oferta))
            
                throw new IllegalArgumentException("Datos inválidos para realizar una carga de oferta");
        
        return new CargaOferta(colaborador, fechaContribucion, (Oferta) args[0]);
    }
}
