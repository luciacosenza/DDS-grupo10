package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class CargaOfertaCreator implements ContribucionCreator {

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        return new CargaOferta(colaborador, fechaContribucion, new Oferta(null, null, null, null));
    }
    
    @Override
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args) {
        if (paraMigrar)
            return crearContribucionDefault(colaborador, fechaContribucion);
        
        if (args.length != 1 ||
            !(args[0] instanceof Oferta)) {
            
            log.log(Level.SEVERE, I18n.getMessage("contribucion.CargaOfertaCreator.crearContribucion_err"));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.CargaOfertaCreator.crearContribucion_exception"));
        }

        return new CargaOferta(colaborador, fechaContribucion, (Oferta) args[0]);
    }
}
