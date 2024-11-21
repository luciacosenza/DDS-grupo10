package com.tp_anual.proyecto_heladeras_solidarias.service.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.repository.colaborador.ColaboradorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class ColaboradorPuntosService { // Esta clase existe para evitar el ciclo de dependencias si tengo lógica de puntos en colaboradorService que debería ser usada por contribucionService

    ColaboradorRepository colaboradorRepository;

    public ColaboradorPuntosService(ColaboradorRepository vColaboradorRepository) {
        colaboradorRepository = vColaboradorRepository;
    }

    public Colaborador obtenerColaborador(Long colaboradorId) {
        return colaboradorRepository.findById(colaboradorId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public Colaborador guardarColaborador(Colaborador colaborador) {
        return colaboradorRepository.save(colaborador);
    }

    public void sumarPuntos(Long colaboradorId, Double puntosASumar) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);
        colaborador.sumarPuntos(puntosASumar);
        guardarColaborador(colaborador);
    }

    public void restarPuntos(Long colaboradorId, Double puntosARestar) {
        Colaborador colaborador = obtenerColaborador(colaboradorId);
        colaborador.restarPuntos(puntosARestar);
        guardarColaborador(colaborador);
    }
}
