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
        completada = false;
    }

    // obtenerDetalles()
    
    @Override
    protected void validarIdentidad() {}

    @Override
    protected void accionar() {
        oferta.darDeAlta();
    }

    @Override
    protected void calcularPuntos() {} // Esta contribucion no entra entre las que suman puntos

}
