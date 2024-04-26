package src;

import java.util.Date;

public class Vianda {
    private String comida;
    private Heladera heladera;
    private Colaborador colaborador;
    private Date fechaCaducidad;
    private Date fechaDonacion;
    private Integer calorias;
    private Integer peso;
    private Boolean entregada;

    public Vianda(String vComida, Heladera vHeladera, Colaborador vColaborador, Date vFechaCacudicad, Date vFechaDonacion, Integer vCalorias, Integer vPeso, Boolean vEntregada) {
        comida = vComida;
        heladera = vHeladera;
        colaborador = vColaborador;
        fechaCaducidad = vFechaCacudicad;
        fechaDonacion = vFechaDonacion;
        calorias = vCalorias;
        peso = vPeso;
        entregada = vEntregada;
    }
}
