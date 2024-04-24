package src;

import java.util.Date;

public class DistribucionViandas extends Contribucion {
    private Heladera origen;
    private Heladera destino;
    private Integer cantidadViandasAMover;
    private motivoDistribucion motivo;

    enum motivoDistribucion {
        DESPERFECTO_EN_LA_HELADERA,
        FALTA_DE_VIANDAS_EN_DESTINO
    }

    public DistribucionViandas(Colaborador colab, Date fechaC, Heladera helaO, Heladera helaD, Integer cantV, motivoDistribucion motiv) {
        colaborador = colab;
        fechaContribucion = fechaC;
        origen = helaO;
        destino = helaD;
        cantidadViandasAMover = cantV;
        motivo = motiv;
    }
}
