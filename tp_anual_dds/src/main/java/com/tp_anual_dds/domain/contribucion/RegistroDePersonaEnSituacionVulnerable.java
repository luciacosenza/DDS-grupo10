package com.tp_anual_dds.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.tarjeta.Tarjeta;
import com.tp_anual_dds.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;

public class RegistroDePersonaEnSituacionVulnerable extends Contribucion {
    private final TarjetaPersonaEnSituacionVulnerable tarjetaAsignada;
    private final Double multiplicador_puntos = 2d;
    
    public RegistroDePersonaEnSituacionVulnerable(Colaborador vColaborador, LocalDateTime vFechaContribucion, TarjetaPersonaEnSituacionVulnerable vTarjetaAsignada) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        tarjetaAsignada = vTarjetaAsignada;
        completada = false;
    }

    public Tarjeta getTarjetaAsignada() {
        return tarjetaAsignada;
    }

    // TODO: public void obtenerDetalles()
    
    @Override
    public void validarIdentidad() {
        if(colaborador.getDomicilio() == null)
            throw new IllegalArgumentException("El colaborador aspirante no posee domicilio. Para registrar personas en situación vulnerable debe actualizar su información");
    }

    @Override
    protected void calcularPuntos() {
        colaborador.sumarPuntos(multiplicador_puntos);
    }
}