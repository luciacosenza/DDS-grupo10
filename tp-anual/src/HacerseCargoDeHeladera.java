package src;

import java.util.Date;

public class HacerseCargoDeHeladera extends Contribucion {
    private String empresaResponsable;
    private Heladera heladeraResponsable;

    public HacerseCargoDeHeladera(Colaborador colab, Date fechaC, String eRespo, Heladera hRespo) {
        colaborador = colab;
        fechaContribucion = fechaC;
        empresaResponsable = eRespo;
        heladeraResponsable = hRespo;
    }
}
