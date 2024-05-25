package domain;

import java.time.LocalDate;

public class DistribucionViandas extends Contribucion {
    private Heladera origen;
    private Heladera destino;
    private Integer cantidadViandasAMover;
    private MotivoDistribucion motivo;

    enum MotivoDistribucion {
        DESPERFECTO_EN_LA_HELADERA,
        FALTA_DE_VIANDAS_EN_DESTINO
    }

    public DistribucionViandas(Colaborador vColaborador, LocalDate vFechaContribucion, Heladera vOrigen, Heladera vDestino, Integer vCantidadViandasAMover, MotivoDistribucion vMotivo) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        origen = vOrigen;
        destino = vDestino;
        cantidadViandasAMover = vCantidadViandasAMover;
        motivo = vMotivo;
    }

    public Integer cantidadViandasAMover(){
        return cantidadViandasAMover;
    }

    // obtenerDetalles()
    
    public void validarIdentidad(Colaborador colaboradorAspirante) {
        if(!esColaboradorHumano(colaboradorAspirante)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Humano");
        }
    }

    public void accionar() {
        Vianda viandaAux;

        for(Integer i = 0; i < cantidadViandasAMover || origen.viandas().isEmpty(); i++) {
            viandaAux = origen.retirarVianda();
            destino.agregarVianda(viandaAux);
            System.err.println(viandaAux);  // Esto es temporal, para que no tire errores. La idea es *agregar la vianda movida al sistema*
        }
    }

    public void calculoDePuntos(ColaboradorHumano colaborador) {
        colaborador.sumarPuntos(Double.valueOf(cantidadViandasAMover));;
    }
}
