package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.heladera.Vianda;

public class DistribucionViandas extends Contribucion {
    private Heladera origen;
    private Heladera destino;
    private Integer cantidadViandasAMover;
    private MotivoDistribucion motivo;

    public enum MotivoDistribucion {
        DESPERFECTO_EN_LA_HELADERA,
        FALTA_DE_VIANDAS_EN_DESTINO
    }
    
    public DistribucionViandas(Colaborador vColaborador, LocalDateTime vFechaContribucion, Heladera vOrigen, Heladera vDestino, Integer vCantidadViandasAMover, MotivoDistribucion vMotivo) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        origen = vOrigen;
        destino = vDestino;
        cantidadViandasAMover = vCantidadViandasAMover;
        motivo = vMotivo;
    }

    // obtenerDetalles()
    
    @Override
    protected void validarIdentidad() {
        if(!esColaboradorHumano(colaborador)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Humano");
        }
    }

    @Override
    protected void accionar() {
        Vianda viandaAux;

        for(Integer i = 0; i < cantidadViandasAMover || origen.getViandas().isEmpty(); i++) {
            viandaAux = origen.retirarVianda();
            destino.agregarVianda(viandaAux);
            System.err.println(viandaAux);  // Esto es temporal, para que no tire errores. La idea es *agregar la vianda movida al sistema*
        }
    }

    @Override
    protected void calcularPuntos() {
        colaborador.sumarPuntos(Double.valueOf(cantidadViandasAMover));
    }
}
