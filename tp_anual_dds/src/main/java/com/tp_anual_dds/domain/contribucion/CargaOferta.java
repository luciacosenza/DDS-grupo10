package com.tp_anual_dds.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.oferta.Oferta;

public class CargaOferta extends Contribucion {
    private final Oferta oferta;

    public CargaOferta(Colaborador vColaborador, LocalDateTime vFechaContribucion, Oferta vOferta) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        oferta = vOferta;
        completada = false;
    }

    // obtenerDetalles()
    
    public Oferta getOferta() {
        return oferta;
    }

    @Override
    public void validarIdentidad() {}

    @Override
    protected void calcularPuntos() {} // Esta contribucion no entra entre las que suman puntos

}