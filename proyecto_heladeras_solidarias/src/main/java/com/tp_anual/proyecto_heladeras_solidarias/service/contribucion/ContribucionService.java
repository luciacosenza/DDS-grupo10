package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log
public abstract class ContribucionService {

    private final ContribucionRepository contribucionRepository;

    protected ContribucionService(ContribucionRepository vContribucionRepository) {
        contribucionRepository = vContribucionRepository;
    }

    public abstract void validarIdentidad(Long contribucionId, Long colaboradorId);

    protected abstract void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados);

    protected abstract void calcularPuntos(Long contribucionId, Long colaboradorId);

    public void confirmar(Long contribucionId, Long colaboradorId, LocalDateTime vFechaContribucion) {
        Contribucion contribucion = contribucionRepository.findById(contribucionId);

        // Podemos agregar lógica para confirmar que la Contribución fue efectivamente realizada

        contribucion.marcarComoCompletada();
        contribucion.setFechaContribucion(vFechaContribucion);   // Actualizo la fecha de contribución al momento que se confirma la realización de la Contribución
        calcularPuntos(contribucionId, colaboradorId);
    }
}
