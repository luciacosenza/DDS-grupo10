package com.tp_anual.proyecto_heladeras_solidarias.service.oferta;

import com.tp_anual.proyecto_heladeras_solidarias.exception.oferta.PuntosInsuficientesException;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.oferta.OfertaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Service
@Log
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    private final I18nService i18nService;

    public OfertaService(OfertaRepository vOfertaRepository, I18nService vI18nService) {
        ofertaRepository = vOfertaRepository;

        i18nService = vI18nService;
    }

    public Oferta obtenerOferta(Long ofertaId) {
        return ofertaRepository.findById(ofertaId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public List<Oferta> obtenerOfertas() {
        return new ArrayList<>(ofertaRepository.findAll());
    }

    public Oferta guardarOferta(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public void validarPuntos(Long ofertaId, Double puntos) throws PuntosInsuficientesException {
        Oferta oferta = obtenerOferta(ofertaId);

        if (puntos < oferta.getCosto()) {
            log.log(Level.SEVERE, i18nService.getMessage("oferta.Oferta.validarPuntos_err", puntos, oferta.getNombre(), oferta.getCosto()));
            throw new PuntosInsuficientesException();
        }
    }
}
