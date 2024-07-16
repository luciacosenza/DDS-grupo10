package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.heladera.Vianda;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva.MotivoSolicitud;

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

    public Heladera getOrigen() {
        return origen;
    }

    public Heladera getDestino() {
        return destino;
    }

    public Integer getCantidadViandasAMover() {
        return cantidadViandasAMover;
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
        ColaboradorHumano colaboradorHumano = (ColaboradorHumano) colaborador;
        
        String codigo = ""; // Esto es temporal, posteriormente se vera como crear los codigos
        colaboradorHumano.setTarjeta(new TarjetaColaboradorActiva(codigo, colaboradorHumano));

        ArrayList<Vianda> viandasADistribuir = new ArrayList<>();

        colaboradorHumano.getTarjeta().solicitarApertura(MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION, origen);

        // Aca deberia pasar algo, dado que el colaborador puede tardar en ir a abrir la heladera, incluso quedarse sin tiempo
        
        colaboradorHumano.getTarjeta().intentarApertura(origen);

        for(Integer i = 0; i < cantidadViandasAMover || origen.getViandas().isEmpty(); i++) {
            viandasADistribuir.add(origen.retirarVianda());
        }

        colaboradorHumano.getTarjeta().solicitarApertura(MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION, destino);

        // Aca deberia pasar algo, dado que el colaborador puede tardar en ir a abrir la heladera, incluso quedarse sin tiempo
        
        colaboradorHumano.getTarjeta().intentarApertura(destino);

        for(Vianda vianda : viandasADistribuir) {
            destino.agregarVianda(vianda);
        }

        System.out.println(this); // Esto es temporal, para que no tire errores. La logica es *registrar la donacion en el sistema*
    }

    @Override
    protected void calcularPuntos() {
        colaborador.sumarPuntos(Double.valueOf(cantidadViandasAMover));
    }
}
