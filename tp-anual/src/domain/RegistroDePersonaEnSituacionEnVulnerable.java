package domain;

import java.time.LocalDateTime;

public class RegistroDePersonaEnSituacionEnVulnerable extends Contribucion {
    private Tarjeta tarjetaAsignada;
    
    public RegistroDePersonaEnSituacionEnVulnerable(Colaborador vColaborador, LocalDateTime vFechaContribucion, Tarjeta vTarjetaAsignada) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        tarjetaAsignada = vTarjetaAsignada;
    }

    public Tarjeta tarjetaAsignada() {
        return tarjetaAsignada;
    }

    // obtenerDetalles()
    
    public void validarIdentidad(Colaborador colaboradorAspirante) {
        if(!esColaboradorHumano(colaboradorAspirante) && colaboradorAspirante.getDomicilio() == null) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Humano");
        }
    }

    public void accionar() {
        tarjetaAsignada.getTitular().setTarjeta(tarjetaAsignada);
        System.out.println(tarjetaAsignada);    // Esto es temporal, para que no tire errores. La idea es *agregar la tarjeta al sistema*
    }

    public void calcularPuntos(ColaboradorHumano colaborador) {
        colaborador.sumarPuntos(2d);;
    }
}