package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;

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

    public void marcarComoCompletada() {
        completada = true;
    }

    public void obtenerDetalles() {
        System.out.println("Contribucion: " + getClass().getSimpleName() + "\n");
        System.out.println("Colaborador:");
        colaborador.obtenerDetalles();
        System.out.println("\n");
        System.out.println("Fecha: " + fechaContribucion);
    }

    public abstract void validarIdentidad();

    protected abstract void calcularPuntos();

    public void confirmar(LocalDateTime vFechaContribucion) {
        // Podemos agregar lógica para confirmar que la Contribución fue efectivamente realizada
        marcarComoCompletada();
        setFechaContribucion(vFechaContribucion);   // Actualizo la fecha de contribución al momento que se confirma la realización de la Contribución
        calcularPuntos();
    }
}
