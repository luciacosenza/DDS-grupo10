package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.Vianda;


public class DonacionVianda extends Contribucion {
    private Vianda vianda;
    private HeladeraActiva heladera;
    private final Double multiplicador_puntos = 1.5;

    public DonacionVianda(Colaborador vColaborador, LocalDateTime vFechaContribucion, Vianda vVianda, HeladeraActiva vHeladera) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        vianda = vVianda;
        heladera = vHeladera;
        completada = false;
    }
    
    public HeladeraActiva getHeladera() {
        return heladera;
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
        /*
        ColaboradorHumano colaboradorHumano = (ColaboradorHumano) colaborador;
        
        String codigo = ""; // Esto es temporal, posteriormente se vera como crear los codigos
        colaboradorHumano.setTarjeta(new TarjetaColaboradorActiva(codigo, colaboradorHumano));

        colaboradorHumano.getTarjeta().solicitarApertura(MotivoSolicitud.INGRESAR_DONACION, heladera);
        
        // Aca deberia pasar algo, dado que el colaborador puede tardar en ir a abrir la heladera, incluso quedarse sin tiempo
        
        colaboradorHumano.getTarjeta().intentarApertura(heladera);
        heladera.agregarVianda(vianda);
        */ // Lo mas probable es que este metodo sea eliminado
    }

    @Override
    protected void calcularPuntos() {
        colaborador.sumarPuntos(multiplicador_puntos);
    }
}