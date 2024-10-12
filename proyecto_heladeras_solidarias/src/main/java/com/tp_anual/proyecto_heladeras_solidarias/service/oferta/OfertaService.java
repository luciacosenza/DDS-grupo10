package com.tp_anual.proyecto_heladeras_solidarias.service.oferta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.oferta.OfertaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@Log
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final ColaboradorService colaboradorService;

    public OfertaService(OfertaRepository vOfertaRepository, ColaboradorService vColaboradorService) {
        ofertaRepository = vOfertaRepository;
        colaboradorService = vColaboradorService;
    }

    public Oferta obtenerOferta(Long ofertaId) {
        return ofertaRepository.findById(ofertaId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public Oferta guardarOferta(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public void validarPuntos(Long ofertaId, Long colaboradorId) {
        Oferta oferta = obtenerOferta(ofertaId);
        Colaborador colaborador = colaboradorService.obtenerColaborador(colaboradorId);

        Double puntosColaborador = colaborador.getPuntos();
        if (puntosColaborador < oferta.getCosto()) {
            log.log(Level.SEVERE, I18n.getMessage("oferta.Oferta.validarPuntos_err", colaborador.getPersona().getNombre(2), colaborador.getPuntos(), oferta.getCosto(), oferta.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("oferta.Oferta.validarPuntos_exception"));
        }
    }
}
