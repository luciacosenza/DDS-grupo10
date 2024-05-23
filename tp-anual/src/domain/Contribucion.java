package domain;

import java.time.LocalDate;

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDate fechaContribucion;

    public LocalDate fechaContribucion(){
        return fechaContribucion;
    }

    // obtenerDetalles()

    public Boolean esColaboradorHumano(Colaborador colaboradorAspirante) {
        return colaboradorAspirante.getClass() == ColaboradorHumano.class;
    }

    public Boolean esColaboradorJuridico(Colaborador colaboradorAspirante) {
        return colaboradorAspirante.getClass() == ColaboradorJuridico.class;
    }

    public abstract void validarIdentidad(Colaborador colaboradorAspirante);

    // public abstract void accionar(/* Completar de ser necesario */);
    
    public void contribuir(Colaborador colaboradorAspirante) {
        validarIdentidad(colaboradorAspirante);
        // accionar();
    }
}
