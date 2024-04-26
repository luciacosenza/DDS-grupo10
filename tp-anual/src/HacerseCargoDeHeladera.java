package src;

import java.util.Date;

public class HacerseCargoDeHeladera extends Contribucion {
    private String empresaResponsable;
    private Heladera heladeraResponsable;

    public HacerseCargoDeHeladera(Colaborador vColaborador, Date vFechaContribucion, String vEmpresaResponsable, Heladera vHeladeraResponsable) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        empresaResponsable = vEmpresaResponsable;
        heladeraResponsable = vHeladeraResponsable;
    }
}
