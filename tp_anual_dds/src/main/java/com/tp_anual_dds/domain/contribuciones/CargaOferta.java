package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.oferta.Oferta;

public class CargaOferta extends Contribucion {
    private Oferta oferta;

    public CargaOferta(Colaborador vColaborador, LocalDateTime vFechaContribucion, Oferta vOferta) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        oferta = vOferta;
    }

    // obtenerDetalles()
    
    @Override
    protected void validarIdentidad() {}

    @Override
    protected void accionar() {
        System.out.println(oferta); // Esto es temporal, para que no tire errores. La idea es *agregar la oferta al sistema*
    }

    @Override
    protected void calcularPuntos() {} // Esta contribucion no entra entre las que suman puntos

}