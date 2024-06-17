package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDateTime fechaContribucion;

    public LocalDateTime getFechaContribucion() {
        return fechaContribucion;
    }

    // obtenerDetalles()

    protected Boolean esColaboradorHumano(Colaborador colaboradorAspirante) {
        return colaboradorAspirante.getClass() == ColaboradorHumano.class;
    }

    protected Boolean esColaboradorJuridico(Colaborador colaboradorAspirante) {
        return colaboradorAspirante.getClass() == ColaboradorJuridico.class;
    }

    protected abstract void validarIdentidad();

    protected abstract void accionar();

    protected abstract void calcularPuntos();
    
    public void contribuir() {
        validarIdentidad();
        accionar();
        calcularPuntos();
    }
}
