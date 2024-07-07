package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.tarjeta.Tarjeta;
import com.tp_anual_dds.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;

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