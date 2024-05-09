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
    // validarIdentidad()
}
