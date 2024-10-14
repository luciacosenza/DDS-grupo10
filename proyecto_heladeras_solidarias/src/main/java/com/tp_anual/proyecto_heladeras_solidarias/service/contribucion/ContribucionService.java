package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;

import java.time.LocalDateTime;

public abstract class ContribucionService {

    public ContribucionService() {}

    public abstract Contribucion obtenerContribucion(Long contribucionId);

    public abstract Contribucion guardarContribucion(Contribucion contribucion);

    public void marcarComoCompletada(Long contribucionId) {
        Contribucion contribucion = obtenerContribucion(contribucionId);
        contribucion.setCompletada(true);
        guardarContribucion(contribucion);
    }

    public void sumoPuntos(Long contribucionId) {
        Contribucion contribucion = obtenerContribucion(contribucionId);
        contribucion.setYaSumoPuntos(true);
        guardarContribucion(contribucion);
    }

    public void seCompletoYSumoPuntos(Long contribucionId) {
        marcarComoCompletada(contribucionId);
        sumoPuntos(contribucionId);
    }

    public abstract void validarIdentidad(Long contribucionId, Colaborador colaborador);

    protected abstract void confirmarSumaPuntos(Long contribucionId, Colaborador colaborador, Double puntosSumados);

    protected abstract void calcularPuntos();

    public void confirmar(Long contribucionId, LocalDateTime vFechaContribucion) {
        Contribucion contribucion = obtenerContribucion(contribucionId);

        // Podemos agregar lógica para confirmar que la Contribución fue efectivamente realizada

        marcarComoCompletada(contribucionId);
        contribucion.setFechaContribucion(vFechaContribucion);   // Actualizo la fecha de contribución al momento que se confirma la realización de la Contribución
        guardarContribucion(contribucion);  // Guardo la contribución porque el setter no está implementado en el service, por lo que no la guarda y hay que hacerlo manualmente
    }
}
