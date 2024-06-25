package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Vianda;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva;


public class DonacionVianda extends Contribucion {
    private Vianda vianda;

    public DonacionVianda(Colaborador vColaborador, LocalDateTime vFechaContribucion, Vianda vVianda) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        vianda = vVianda;
    }

    // obtenerDetalles()
    
    @Override
    protected void validarIdentidad() {
        if(!esColaboradorHumano(colaborador)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Humano");
        }

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

        System.out.println(vianda); // Esto es temporal, para que no tire errores. La logica es *registrar la vianda en el sistema*
    }

    @Override
    protected void calcularPuntos() {
        final Double MULTIPLICADOR_PUNTOS = 1.5;
        
        colaborador.sumarPuntos(MULTIPLICADOR_PUNTOS);
    }
}