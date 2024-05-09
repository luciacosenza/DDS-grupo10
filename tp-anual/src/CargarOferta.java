package src;

import java.time.LocalDate;

public class CargarOferta extends Contribucion {
    private Oferta oferta;

    public CargarOferta(Colaborador vColaborador, LocalDate vFechaContribucion, Oferta vOferta) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        oferta = vOferta;
    }

    // obtenerDetalles()
    
    public void validarIdentidad(Colaborador colaboradorAspirante) {
        if(!esColaboradorJuridico(colaboradorAspirante)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Juridico");
        }
    }

    // accionar()
}
