package domain;

import java.time.LocalDate;
import java.time.Period;

public class HacerseCargoDeHeladera extends Contribucion {
    private Heladera heladeraResponsable;

    public HacerseCargoDeHeladera(Colaborador vColaborador, LocalDate vFechaContribucion, Heladera vHeladeraResponsable) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        heladeraResponsable = vHeladeraResponsable;
    }

    // obtenerDetalles()
    
    public void validarIdentidad(Colaborador colaboradorAspirante) {
        if(!esColaboradorJuridico(colaboradorAspirante)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Juridico");
        }
    }
    
    public Integer mesesHaciendoseCargo() {
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaContribucion, fechaActual);
        Integer meses = periodo.getMonths();
        Integer anios = periodo.getYears();

        meses += anios * 12;

        return meses;
    }

    // accionar()
}
