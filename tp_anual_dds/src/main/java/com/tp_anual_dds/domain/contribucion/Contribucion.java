package com.tp_anual_dds.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.colaborador.ColaboradorJuridico;

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

    // obtenerDetalles()

    protected Boolean esColaboradorHumano(Colaborador colaboradorAspirante) {
        return colaboradorAspirante.getClass() == ColaboradorHumano.class;
    }

    protected Boolean esColaboradorJuridico(Colaborador colaboradorAspirante) {
        return colaboradorAspirante.getClass() == ColaboradorJuridico.class;
    }

    public abstract void validarIdentidad();

    protected abstract void calcularPuntos();

    public void confirmar(LocalDateTime vFechaContribucion) {
        // Podemos agregar logica para confirmar que la Contribucion fue efectivamente realizada
        completada = true;
        setFechaContribucion(vFechaContribucion);   // Para actualizar fechaContribucion al momento en el que se confirma la realizacion de la Contribucion
        calcularPuntos();
    }
}
