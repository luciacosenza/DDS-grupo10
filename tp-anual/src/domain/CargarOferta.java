package domain;

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

    public void accionar() {
        System.out.println(oferta); // Esto es temporal, para que no tire errores. La idea es *agregar la oferta al sistema*
    }

}
