package com.tp_anual_dds.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDateTime fechaContribucion;
    protected Boolean completada;

    public Colaborador getColaborador() {
        return colaborador;
    }

    public LocalDateTime getFechaContribucion() {
        return fechaContribucion;
    }

    public Boolean estaCompletada() {
        return completada;
    }

    public void setFechaContribucion(LocalDateTime vFechaContribucion) {
        fechaContribucion = vFechaContribucion;
    }

    // public abstract void obtenerDetalles() (TODO)

    public abstract void validarIdentidad();

    protected abstract void calcularPuntos();

    public void confirmar(LocalDateTime vFechaContribucion) {
        // Podemos agregar lógica para confirmar que la Contribución fue efectivamente realizada
        completada = true;
        setFechaContribucion(vFechaContribucion);   // Actualizo la fecha de contribución al momento que se confirma la realización de la Contribución
        calcularPuntos();
    }
}
