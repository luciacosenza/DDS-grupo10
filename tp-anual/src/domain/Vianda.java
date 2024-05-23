package domain;

import java.time.LocalDate;

public class Vianda {
    private String comida;
    private Heladera heladera;
    private Colaborador colaborador;
    private LocalDate fechaCaducidad;
    private LocalDate fechaDonacion;
    private Integer calorias;
    private Integer peso;
    private Boolean entregada;

    public Vianda(String vComida, Heladera vHeladera, Colaborador vColaborador, LocalDate vFechaCacudicad, LocalDate vFechaDonacion, Integer vCalorias, Integer vPeso, Boolean vEntregada) {
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
