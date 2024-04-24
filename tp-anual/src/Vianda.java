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

    public Vianda(String comid, Heladera helad, Colaborador colab, Date fechaC, Date fechaD, Integer cal, Integer pes, Boolean entre) {
        comida = comid;
        heladera = helad;
        colaborador = colab;
        fechaCaducidad = fechaC;
        fechaDonacion = fechaD;
        calorias = cal;
        peso = pes;
        entregada = entre;
    }
}
