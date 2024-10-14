package com.tp_anual.proyecto_heladeras_solidarias.service.oferta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.oferta.OfertaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Level;

@Service
@Log
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    public OfertaService(OfertaRepository vOfertaRepository) {
        ofertaRepository = vOfertaRepository;
    }

    public Oferta obtenerOferta(Long ofertaId) {
        return ofertaRepository.findById(ofertaId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public ArrayList<Oferta> obtenerOfertas() {
        return new ArrayList<>(ofertaRepository.findAll());
    }

    public Oferta guardarOferta(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public void validarPuntos(Long ofertaId, Double puntos) {
        Oferta oferta = obtenerOferta(ofertaId);

        if (puntos < oferta.getCosto()) {
            log.log(Level.SEVERE, I18n.getMessage("oferta.Oferta.validarPuntos_err", puntos, oferta.getNombre(), oferta.getCosto()));
            throw new UnsupportedOperationException(I18n.getMessage("oferta.Oferta.validarPuntos_exception"));
        }
    }
}
