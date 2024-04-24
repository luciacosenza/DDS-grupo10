package src;

import java.util.Date;

public class DonacionDinero extends Contribucion {
    private Integer monto;
    private frecuenciaDePago frecuencia;
    
    enum frecuenciaDePago {
        // Completar
    }

    public DonacionDinero(Colaborador colab, Date fechaC, Integer mont, frecuenciaDePago frecu) {
        colaborador = colab;
        fechaContribucion = fechaC;
        monto = mont;
        frecuencia = frecu;
    }
}
