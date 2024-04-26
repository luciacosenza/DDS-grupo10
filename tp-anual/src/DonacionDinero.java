package src;

import java.util.Date;

public class DonacionDinero extends Contribucion {
    private Integer monto;
    private FrecuenciaDePago frecuencia;
    
    enum FrecuenciaDePago {
        SEMANAL,
        MENSUAL,
        SEMESTRAL,
        ANUAL,
        UNICA_VEZ
    }

    public DonacionDinero(Colaborador vColaborador, Date vFechaContribucion, Integer vMonto, FrecuenciaDePago vFrecuencia) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        monto = vMonto;
        frecuencia = vFrecuencia;
    }
}
