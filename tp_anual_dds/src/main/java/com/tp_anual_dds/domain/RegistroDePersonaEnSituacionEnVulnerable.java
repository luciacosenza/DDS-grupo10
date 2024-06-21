package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public class RegistroDePersonaEnSituacionEnVulnerable extends Contribucion {
    private TarjetaPersonaEnSituacionVulnerable tarjetaAsignada;
    
    public RegistroDePersonaEnSituacionEnVulnerable(Colaborador vColaborador, LocalDateTime vFechaContribucion, TarjetaPersonaEnSituacionVulnerable vTarjetaAsignada) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        tarjetaAsignada = vTarjetaAsignada;
    }

    public Tarjeta getTarjetaAsignada() {
        return tarjetaAsignada;
    }

    // obtenerDetalles()
    
    @Override
    protected void validarIdentidad() {
        if(!esColaboradorHumano(colaborador)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Humano");
        }
        
        if(colaborador.getDomicilio() == null) {
            throw new IllegalArgumentException("El colaborador aspirante no posee domicilio. Para Registrar Personas Vulnerables debe actualizar su información.");
        }
    }

    @Override
    protected void accionar() {
        tarjetaAsignada.getTitular().setTarjeta(tarjetaAsignada);
        System.out.println(tarjetaAsignada);    // Esto es temporal, para que no tire errores. La idea es *agregar la tarjeta al sistema*
    }

    @Override
    protected void calcularPuntos() {
        final Double MULTIPLICADOR_PUNTOS = 2d;
        colaborador.sumarPuntos(MULTIPLICADOR_PUNTOS);
    }
}