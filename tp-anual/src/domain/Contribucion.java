package domain;

import java.time.LocalDateTime;

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDateTime fechaContribucion;

    public LocalDateTime getFechaContribucion(){
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

    public abstract void accionar();
    
    public void contribuir(Colaborador colaboradorAspirante) {
        validarIdentidad(colaboradorAspirante);
        accionar();
    }
}
