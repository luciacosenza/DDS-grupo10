package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DistribucionViandas extends Contribucion {
    private static final Logger logger = Logger.getLogger(DistribucionViandas.class.getName());
    private final HeladeraActiva origen;
    private final HeladeraActiva destino;
    private final Integer cantidadViandasAMover;
    private final MotivoDistribucion motivo;

    public enum MotivoDistribucion {
        DESPERFECTO_EN_LA_HELADERA,
        FALTA_DE_VIANDAS_EN_DESTINO
    }
    
    public DistribucionViandas(Colaborador vColaborador, LocalDateTime vFechaContribucion, HeladeraActiva vOrigen, HeladeraActiva vDestino, Integer vCantidadViandasAMover, MotivoDistribucion vMotivo) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        origen = vOrigen;
        destino = vDestino;
        cantidadViandasAMover = vCantidadViandasAMover;
        motivo = vMotivo;
        completada = false;
    }

    public HeladeraActiva getOrigen() {
        return origen;
    }

    public HeladeraActiva getDestino() {
        return destino;
    }

    public Integer getCantidadViandasAMover() {
        return cantidadViandasAMover;
    }

    public MotivoDistribucion getMotivo() {
        return motivo;
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_origen", origen.getNombre()));
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_destino", destino.getNombre()));
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_candidad_viandas_a_mover", cantidadViandasAMover));
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_motivo", motivo));
    }
    
    @Override
    public void validarIdentidad() {
        if(colaborador.getDomicilio() == null) {
            logger.log(Level.SEVERE, I18n.getMessage("contribucion.DistribucionViandas.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DistribucionViandas.validarIdentidad_exception"));
        }
    }

    @Override
    protected void calcularPuntos() {
        colaborador.sumarPuntos(Double.valueOf(cantidadViandasAMover));
    }
}
