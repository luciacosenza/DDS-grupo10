package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public class CargarOferta extends Contribucion {
    private Oferta oferta;

    public CargarOferta(Colaborador vColaborador, LocalDateTime vFechaContribucion, Oferta vOferta) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        oferta = vOferta;
    }

    // obtenerDetalles()
    
    @Override
    public void validarIdentidad(Colaborador colaboradorAspirante) {
        if(!esColaboradorJuridico(colaboradorAspirante)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Juridico");
        }
    }

    @Override
    public void accionar() {
        System.out.println(oferta); // Esto es temporal, para que no tire errores. La idea es *agregar la oferta al sistema*
    }

}
