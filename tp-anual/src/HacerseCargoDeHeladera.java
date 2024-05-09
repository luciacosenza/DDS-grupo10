package src;

import java.time.LocalDate;
import java.time.Period;

public class HacerseCargoDeHeladera extends Contribucion {
    private String empresaResponsable;
    private Heladera heladeraResponsable;

    public HacerseCargoDeHeladera(Colaborador vColaborador, LocalDate vFechaContribucion, String vEmpresaResponsable, Heladera vHeladeraResponsable) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        empresaResponsable = vEmpresaResponsable;
        heladeraResponsable = vHeladeraResponsable;
    }

    // obtenerDetalles()
    // validarIdentidad()
    
    public Integer mesesHaciendoseCargo() {
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaContribucion, fechaActual);
        Integer meses = periodo.getMonths();
        Integer anios = periodo.getYears();

        meses += anios * 12;

        return meses;
    }
}
