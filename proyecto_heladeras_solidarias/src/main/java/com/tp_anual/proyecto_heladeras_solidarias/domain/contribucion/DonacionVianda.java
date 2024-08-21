package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Vianda;


public class DonacionVianda extends Contribucion {
    private final Vianda vianda;
    private final HeladeraActiva heladera;
    private final Double multiplicador_puntos = 1.5;

    public DonacionVianda(Colaborador vColaborador, LocalDateTime vFechaContribucion, Vianda vVianda, HeladeraActiva vHeladera) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        vianda = vVianda;
        heladera = vHeladera;
        completada = false;
    }
    
    public Vianda getVianda() {
        return vianda;
    }

    public HeladeraActiva getHeladera() {
        return heladera;
    }

    // TODO: public void obtenerDetalles()
    
    @Override
    public void validarIdentidad() {
        if (colaborador.getDomicilio() == null)
            throw new IllegalArgumentException("El colaborador aspirante no posee domicilio. Para recibir la tarjeta solidaria debe actualizar su información");
    }

    @Override
    protected void calcularPuntos() {
        colaborador.sumarPuntos(multiplicador_puntos);
    }
}