package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.CargaOferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class CargaOfertaCreator implements ContribucionCreator {

    public CargaOfertaCreator() {}

    @Override
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion) {
        CargaOferta cargaOferta = new CargaOferta(colaborador, fechaContribucion, new Oferta(null, null, null, null));
        cargaOferta.seCompletoYSumoPuntos();    // Llamo directamente al método de cargaOferta, porque no quiero que se guarde en este momento

        return cargaOferta;
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
