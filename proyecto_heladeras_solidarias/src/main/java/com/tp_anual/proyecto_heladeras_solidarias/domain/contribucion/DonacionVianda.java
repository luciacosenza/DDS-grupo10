package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DonacionVianda extends Contribucion {
    private static final Logger logger = Logger.getLogger(DonacionVianda.class.getName());
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

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.DonacionVianda.obtenerDetalles_out_vianda_comida", vianda.getComida()));
        System.out.println(I18n.getMessage("contribucion.DonacionVianda.obtenerDetalles_out_vianda_comida", heladera.getNombre()));
    }
    
    @Override
    public void validarIdentidad() {
        if (colaborador.getDomicilio() == null) {
            logger.log(Level.SEVERE, I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_exception"));
        }
    }

    @Override
    protected void confirmarSumaPuntos(Double puntosSumados) {
        logger.log(Level.INFO, I18n.getMessage("contribucion.DonacionVianda.calcularPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos() {
        Double puntosASumar = multiplicador_puntos;
        colaborador.sumarPuntos(multiplicador_puntos);
        confirmarSumaPuntos(puntosASumar);
    }
}