package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public class DonacionVianda extends Contribucion {
    private Vianda vianda;

    public DonacionVianda(Colaborador vColaborador, LocalDateTime vFechaContribucion, Vianda vVianda) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        vianda = vVianda;
    }

    // obtenerDetalles()
    
    @Override
    protected void validarIdentidad() {
        if(!esColaboradorHumano(colaborador)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Humano");
        }
    }

    @Override
    protected void accionar() {
        System.out.println(vianda); // Esto es temporal, para que no tire errores. La logica es *registrar la vianda en el sistema*

    }

    @Override
    protected void calcularPuntos() {
        final Double MULTIPLICADOR_PUNTOS = 1.5;
        
        colaborador.sumarPuntos(MULTIPLICADOR_PUNTOS);;
    }
}