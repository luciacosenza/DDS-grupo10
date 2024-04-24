package src;

import java.util.Date;

public class DonacionVianda extends Contribucion {
    private Vianda vianda;

    public DonacionVianda(Colaborador colab, Date fechaC, Vianda viand) {
        colaborador = colab;
        fechaContribucion = fechaC;
        vianda = viand;
    }
}
