package domain;

import java.time.LocalDate;
import java.time.Period;

public class HacerseCargoDeHeladera extends Contribucion {
    private Heladera heladeraObjetivo;

    public HacerseCargoDeHeladera(Colaborador vColaborador, LocalDate vFechaContribucion, Heladera vHeladeraObjetivo) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        heladeraObjetivo = vHeladeraObjetivo;
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

    public void accionar() {
        System.out.println(heladeraObjetivo); // Esto es temporal, para que no tire errores. La logica es *registrar la heladera en el sistema*
    }
}