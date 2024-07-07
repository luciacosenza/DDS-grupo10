package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.heladera.Vianda;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva;

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
        if(colaborador.getDomicilio() == null) {
            throw new IllegalArgumentException("El colaborador aspirante no posee domicilio. Para recibir la Tarjeta Solidaria debe actualizar su información");
        }
    }

    @Override
    protected void accionar() {
        // Hacemos un downcast para poder utilizar el metodo setTarjeta()
        ColaboradorHumano colaboradorHumano = (ColaboradorHumano) colaborador;
        
        String codigo = ""; // Esto es temporal, posteriormente se vera como crear los codigos

        colaboradorHumano.setTarjeta(new TarjetaColaboradorActiva(codigo, colaboradorHumano));

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
