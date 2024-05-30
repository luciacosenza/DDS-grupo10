package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public class CargaOferta extends Contribucion {
    private Oferta oferta;

    public CargaOferta(Colaborador vColaborador, LocalDateTime vFechaContribucion, Oferta vOferta) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        oferta = vOferta;
    }

    // obtenerDetalles()
    
    @Override
    public void validarIdentidad() {
        if(!esColaboradorJuridico(colaborador)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Jurídico");
        }
    }

    @Override
    public void accionar() {
        System.out.println(oferta); // Esto es temporal, para que no tire errores. La idea es *agregar la oferta al sistema*
    }

    @Override
    public void calcularPuntos() {} // Esta contribucion no entra entre las que suman puntos

}
