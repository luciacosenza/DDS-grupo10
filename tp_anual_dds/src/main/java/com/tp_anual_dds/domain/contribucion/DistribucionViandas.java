package com.tp_anual_dds.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class DistribucionViandas extends Contribucion {
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

    // public void obtenerDetalles() (TODO)
    
    @Override
    public void validarIdentidad() {
        if(colaborador.getDomicilio() == null) {
            throw new IllegalArgumentException("El colaborador aspirante no posee domicilio. Para recibir la tarjeta solidaria debe actualizar su información");
        }
    }

    @Override
    protected void calcularPuntos() {
        colaborador.sumarPuntos(Double.valueOf(cantidadViandasAMover));
    }
}
