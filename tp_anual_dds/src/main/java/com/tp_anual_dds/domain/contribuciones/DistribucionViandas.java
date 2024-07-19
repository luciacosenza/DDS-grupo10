package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class DistribucionViandas extends Contribucion {
    private HeladeraActiva origen;
    private HeladeraActiva destino;
    private Integer cantidadViandasAMover;
    private MotivoDistribucion motivo;

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

    // obtenerDetalles()
    
    @Override
    public void validarIdentidad() {
        if(colaborador.getDomicilio() == null) {
            throw new IllegalArgumentException("El colaborador aspirante no posee domicilio. Para recibir la Tarjeta Solidaria debe actualizar su información");
        }
    }

    @Override
    protected void calcularPuntos() {
        colaborador.sumarPuntos(Double.valueOf(cantidadViandasAMover));
    }
}
