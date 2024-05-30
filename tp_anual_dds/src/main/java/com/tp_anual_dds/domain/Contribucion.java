package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDateTime fechaContribucion;

    public LocalDateTime getFechaContribucion(){
        return fechaContribucion;
    }

    // obtenerDetalles()

    public Boolean esColaboradorHumano(Colaborador colaboradorAspirante) {
        return colaboradorAspirante.getClass() == ColaboradorHumano.class;
    }

    public Boolean esColaboradorJuridico(Colaborador colaboradorAspirante) {
        return colaboradorAspirante.getClass() == ColaboradorJuridico.class;
    }

    public abstract void validarIdentidad();

    public abstract void accionar();

    public abstract void calcularPuntos();
    
    public void contribuir() {
        validarIdentidad();
        accionar();
        calcularPuntos();
    }
}
