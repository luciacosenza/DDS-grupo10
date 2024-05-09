package src;

import java.time.LocalDate;

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDate fechaContribucion;

    public LocalDate fechaContribucion(){
        return fechaContribucion;
    }

    // obtenerDetalles()
    // validarIdentidad()
    // accionar()
    // contribuir()
}
