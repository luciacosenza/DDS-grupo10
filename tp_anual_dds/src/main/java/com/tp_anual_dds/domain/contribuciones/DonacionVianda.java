package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.heladera.Vianda;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva.MotivoSolicitud;


public class DonacionVianda extends Contribucion {
    private Vianda vianda;
    private Heladera heladera;

    public DonacionVianda(Colaborador vColaborador, LocalDateTime vFechaContribucion, Vianda vVianda, Heladera vHeladera) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        vianda = vVianda;
        heladera = vHeladera;
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

        colaboradorHumano.getTarjeta().solicitarApertura(MotivoSolicitud.INGRESAR_DONACION, heladera);
        
        // Aca deberia pasar algo, dado que el colaborador puede tardar en ir a abrir la heladera, incluso quedarse sin tiempo
        
        colaboradorHumano.getTarjeta().intentarApertura(heladera);
        heladera.agregarVianda(vianda);

        System.out.println(this); // Esto es temporal, para que no tire errores. La logica es *registrar la vianda en el sistema*
    }

    @Override
    protected void calcularPuntos() {
        final Double MULTIPLICADOR_PUNTOS = 1.5;
        
        colaborador.sumarPuntos(MULTIPLICADOR_PUNTOS);
    }
}